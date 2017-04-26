/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.iotmessagereader;

import com.google.gson.Gson;
import com.microsoft.windowsazure.services.servicebus.*;
import com.microsoft.windowsazure.services.servicebus.models.*;
import com.microsoft.windowsazure.Configuration;
import edu.cmu.sensormessage.SensorReadingMessage;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author owenwagoner
 */
public class ReadQueue {

    private ServiceBusContract service;
    private String url = "servicebus.windows.net/";
    private String namespace = "farmiot.";
    private String key1 = "mAxLq2xmPeZAWrHU6Q9QeWV7oFySuxoRBRsuU4VuTT8=";
    private String key2 = "bSboJeK2ZZpWn9Aur9W6m33VvGkfZBC0FZTchEeEmyA=";
    private String endpoint = "Endpoint=sb://farmiot.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=mAxLq2xmPeZAWrHU6Q9QeWV7oFySuxoRBRsuU4VuTT8=";
    private String endpoint2 = "Endpoint=sb://farmiot.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=bSboJeK2ZZpWn9Aur9W6m33VvGkfZBC0FZTchEeEmyA=";
    private String addSensorQueue = "AddSensorQueue";
    private String sensorReadingQueue = "SensorReadingQueue";
    private String deleteSensorQueue = "DeleteSensorQueue";

    public ArrayList ReadMessage() {
        List<String> list = new ArrayList<>();
        Configuration config = ServiceBusConfiguration.configureWithSASAuthentication(namespace, "RootManageSharedAccessKey", key1, url);
        service = ServiceBusService.create(config);
        try {
            ReceiveMessageOptions opts = ReceiveMessageOptions.DEFAULT;
            opts.setReceiveMode(ReceiveMode.PEEK_LOCK);

            while (true) {
                ReceiveQueueMessageResult resultQM
                        = service.receiveQueueMessage("SensorReadingQueue", opts);
                BrokeredMessage message = resultQM.getValue();
                if (message != null && message.getMessageId() != null) {
                    System.out.println("MessageID: " + message.getMessageId());
                    // Display the queue message.
                    System.out.print("From queue: ");
                    byte[] b = new byte[200];
                    String s = null;
                    int numRead = message.getBody().read(b);
                    while (-1 != numRead) {
                        s = new String(b);
                        s = s.trim();
                        System.out.print(s);
                        list.add(s);
                        numRead = message.getBody().read(b);
                    }
                    Gson gson = new Gson();
                    SensorReadingMessage srMessage = gson.fromJson(s, SensorReadingMessage.class);
                    
                    System.out.println();
                    System.out.println("Custom Property: "
                            + message.getProperty("MyProperty"));
                    // Remove message from queue.
                    System.out.println("Deleting this message.");
                    //service.deleteMessage(message);
                } else {
                    System.out.println("Finishing up - no more messages.");
                    break;
                    // Added to handle no more messages.
                    // Could instead wait for more messages to be added.
                }
            }
        } catch (Exception e) {
            System.out.print("Generic exception encountered: ");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return (ArrayList) list;
    }
}
