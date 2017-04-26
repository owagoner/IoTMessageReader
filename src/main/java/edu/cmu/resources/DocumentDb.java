/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.resources;

import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.Database;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import com.microsoft.azure.documentdb.DocumentCollection;
import com.microsoft.azure.documentdb.RequestOptions;
import edu.cmu.sensormessage.SensorReadingMessage;
import java.util.List;

/**
 *
 * @author owenwagoner
 */
public class DocumentDb {

    private static final String DATABASE_ID = "farmiot";
    private static final String COLLECTION_ID = "SensorData";
    private static final String URL = "https://farmiot.documents.azure.com:443/";
    private static final String PRIMARY_KEY = "w179nPwMtyqu46UthNt9l51r2WaCmYCTUzGnQeruisrNQNyfwuLTfOfaOrsUwx870f3lp0kLxvuThVYdiuNofQ==";
    
    private static DocumentClient documentClient;
    private static Database databaseCache;
    private static DocumentCollection collectionCache;

    public DocumentDb() {
        documentClient = new DocumentClient(URL, PRIMARY_KEY,
                new ConnectionPolicy(), ConsistencyLevel.Session);
        databaseCache = getDatabase();
        collectionCache = getCollection();
    }

    public boolean addSensorDataPoint(SensorReadingMessage message){
        try {
            documentClient.createDocument(collectionCache.getSelfLink(), message, new RequestOptions(), true);
            return true;
        } catch (DocumentClientException ex) {
            //Logger.getLogger(DocumentDb.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
    
    
    private Database getDatabase() {
        if (databaseCache == null) {
            // Get the database if it exists
            List<Database> databaseList = documentClient
                    .queryDatabases(
                            "SELECT * FROM root r WHERE r.id='" + DATABASE_ID
                            + "'", null).getQueryIterable().toList();

            if (databaseList.size() > 0) {
                // Cache the database object so we won't have to query for it
                // later to retrieve the selfLink.
                databaseCache = databaseList.get(0);
            } else {
                // Create the database if it doesn't exist.
                try {
                    Database databaseDefinition = new Database();
                    databaseDefinition.setId(DATABASE_ID);

                    databaseCache = documentClient.createDatabase(
                            databaseDefinition, null).getResource();
                } catch (DocumentClientException e) {
                    // TODO: Something has gone terribly wrong - the app wasn't
                    // able to query or create the collection.
                    // Verify your connection, endpoint, and key.
                    e.printStackTrace();
                }
            }
        }

        return databaseCache;
    }

    private DocumentCollection getCollection() {
        if (collectionCache == null) {
            // Get the collection if it exists.
            List<DocumentCollection> collectionList = documentClient
                    .queryCollections(
                            getDatabase().getSelfLink(),
                            "SELECT * FROM root r WHERE r.id='" + COLLECTION_ID
                            + "'", null).getQueryIterable().toList();

            if (collectionList.size() > 0) {
                // Cache the collection object so we won't have to query for it
                // later to retrieve the selfLink.
                collectionCache = collectionList.get(0);
            } else {
                // Create the collection if it doesn't exist.
                try {
                    DocumentCollection collectionDefinition = new DocumentCollection();
                    collectionDefinition.setId(COLLECTION_ID);

                    collectionCache = documentClient.createCollection(
                            getDatabase().getSelfLink(),
                            collectionDefinition, null).getResource();
                } catch (DocumentClientException e) {
                    // TODO: Something has gone terribly wrong - the app wasn't
                    // able to query or create the collection.
                    // Verify your connection, endpoint, and key.
                    e.printStackTrace();
                }
            }
        }

        return collectionCache;
    }

    public boolean addUnvalidatedSensorMessage(SensorReadingMessage message) {
        DocumentCollection collectionCache2 = null;
        String collection_id = "UnvalidatedLog";
        List<DocumentCollection> collectionList = documentClient
                    .queryCollections(
                            getDatabase().getSelfLink(),
                            "SELECT * FROM root r WHERE r.id='" + collection_id
                            + "'", null).getQueryIterable().toList();

            if (collectionList.size() > 0) {
                // Cache the collection object so we won't have to query for it
                // later to retrieve the selfLink.
                collectionCache2 = collectionList.get(0);
            } else {
                // Create the collection if it doesn't exist.
                try {
                    DocumentCollection collectionDefinition = new DocumentCollection();
                    collectionDefinition.setId(collection_id);

                    collectionCache2 = documentClient.createCollection(
                            getDatabase().getSelfLink(),
                            collectionDefinition, null).getResource();
                } catch (DocumentClientException e) {
                    // TODO: Something has gone terribly wrong - the app wasn't
                    // able to query or create the collection.
                    // Verify your connection, endpoint, and key.
                    e.printStackTrace();
                }
            }
        try {
            documentClient.createDocument(collectionCache2.getSelfLink(), message, new RequestOptions(), true);
            return true;
        } catch (DocumentClientException ex) {
            //Logger.getLogger(DocumentDb.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

}
