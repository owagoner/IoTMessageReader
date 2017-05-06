package edu.cmu.resources;

import com.google.gson.Gson;
import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.Document;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import edu.cmu.sensormessage.SensorReadingMessage;

/**
 *
 * @author owenwagoner
 */
public class DocumentDb {

    private static Gson gson = new Gson();
    private static DocumentClient documentClient;
    private final String DATABASE_ID;

    public DocumentDb(String URL, String PRIMARY_KEY, String DATABASE_ID) {
        documentClient = new DocumentClient(URL, PRIMARY_KEY,
                new ConnectionPolicy(), ConsistencyLevel.Session);
        this.DATABASE_ID = DATABASE_ID;
    }

    public boolean addDocument(String COLLECTION_ID, SensorReadingMessage message){
        try {
            //GET JSON
            String messageStr = gson.toJson(message);
            //CREATE DOC
            Document doc = new Document(messageStr);
            //SEND DOC TO DB
            documentClient.createDocument(
                    "dbs/" + DATABASE_ID + "/colls/" + COLLECTION_ID, 
                    doc, 
                    null, 
                    false
            );
            
            System.out.println("Wrote message from: " + message.getSensorHash() 
                    + " to database collection: " + COLLECTION_ID);
            return true;
        } catch (DocumentClientException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

}
