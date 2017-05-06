package edu.cmu.iotmessagereader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.microsoft.windowsazure.services.servicebus.*;
import com.microsoft.windowsazure.services.servicebus.models.*;
import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.exception.ServiceException;
import edu.cmu.resources.DocumentDb;
import edu.cmu.sensormessage.SensorReadingMessage;
import java.io.IOException;


/**
 *
 * @author owenwagoner
 */
public class ReadQueue {

    private ServiceBusContract service;
    private final String url = "servicebus.windows.net/";
    private final String namespace = "farmiot.";
    private final String key = "mAxLq2xmPeZAWrHU6Q9QeWV7oFySuxoRBRsuU4VuTT8=";
    private final String addSensorQueue = "AddSensorQueue";
    private final String sensorReadingQueue = "SensorReadingQueue";
    private final String deleteSensorQueue = "DeleteSensorQueue";
    
    //DOCUMENTDB ACCESS VALUES
    private static final String DATABASE_ID = "farmiot";
    private static final String VALIDATED_COLLECTION = "SensorData";
    private static final String UNVALIDATED_COLLECTION = "UnvalidatedLog";
    private static final String URL = "https://farmiot.documents.azure.com:443/";
    private static final String PRIMARY_KEY = "w179nPwMtyqu46UthNt9l51r2WaCmYCTUzGnQeruisrNQNyfwuLTfOfaOrsUwx870f3lp0kLxvuThVYdiuNofQ==";
    private static final DocumentDb documentDB = new DocumentDb(URL, PRIMARY_KEY, DATABASE_ID);
    
    
    private static Ethereum eth;
    //private String contractAddress;

    public ReadQueue(String contractAddress, String pathToWalletFile, String password){
        //Create Ethereum class with provided address.
        if(eth == null){
            eth = new Ethereum(contractAddress, pathToWalletFile, password);
        }
        
    }
    
    public void ReadMessages() {
        Configuration config = ServiceBusConfiguration
                .configureWithSASAuthentication(
                        namespace, 
                        "RootManageSharedAccessKey", 
                        key, 
                        url
                );
        service = ServiceBusService.create(config);
        
        try {
            ReceiveMessageOptions opts = ReceiveMessageOptions.DEFAULT;
            opts.setReceiveMode(ReceiveMode.PEEK_LOCK);
            
            System.out.println("Reading messages...");
            while (true) {
                ReceiveQueueMessageResult resultQM
                        = service.receiveQueueMessage(sensorReadingQueue, opts);
                BrokeredMessage message = resultQM.getValue();
                if (message != null && message.getMessageId() != null) {
                    //Read message body in
                    byte[] b = new byte[200];
                    String s = "";

                    int numRead = message.getBody().read(b);
                    while (-1 != numRead) {
                        s = s + new String(b);
                        s = s.trim();
                        //System.out.print(s);
                        //list.add(s);
                        numRead = message.getBody().read(b);
                    }
                    
                    System.out.println("Message Read: " + s);
                    Gson gson = new GsonBuilder().create();
                    SensorReadingMessage srMessage = gson.fromJson(s, SensorReadingMessage.class);

                    boolean isValid = validateHash(srMessage);

                    if (isValid) {
                        System.out.println("Message was validated");
                        //Set signature
                        srMessage.setSignature(message.getProperty("Signature").toString());
                       //Write signature to message
                        boolean isStored = documentDB.addDocument(VALIDATED_COLLECTION,srMessage);

                        if (isStored) {
                            // Remove message from queue.
                            System.out.println("Message stored, deleting this message.");
                            service.deleteMessage(message);
                        } else {
                            //Failed to store to database
                            System.out.println("Unable to store data at this time.");
                        }
                    } else {
                        //Not valid... write to log
                        System.out.println("Message reading not valid.");
                        boolean isStored = documentDB.addDocument(UNVALIDATED_COLLECTION,srMessage);
                        if(isStored){
                            service.deleteMessage(message);
                        }else{
                            System.out.println("Unable to store data at this time.");
                        }
                    }
                    System.out.println("****************************************************");
                    System.out.println("****************************************************");
                } else {
                    System.out.println("Waiting for more messages...");
                    break;
                    // Added to handle no more messages.
                    // Could instead wait for more messages to be added.
                }
            }
        } catch (JsonSyntaxException | ServiceException | IOException e) {
            System.out.print("Generic exception encountered: ");
            System.out.println(e.getMessage());
        }
    }

    private boolean validateHash(SensorReadingMessage message) {
        //Validates message hash against blockchain
        return eth.validateSensor(message.getSensorHash());   
    }
}
