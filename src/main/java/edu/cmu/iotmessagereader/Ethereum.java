/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.iotmessagereader;

import edu.cmu.sensormessage.SensorReadingMessage;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

/**
 *
 * @author owenwagoner
 */
public class Ethereum {
    
    public boolean validateSensor(SensorReadingMessage message){
        Web3j web3 = Web3j.build(new HttpService("http://localhost:8086/"));  // defaults to http://localhost:8545/
        
        try {
            Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            
            return true;
        } catch (InterruptedException ex) {
            Logger.getLogger(Ethereum.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ExecutionException ex) {
            Logger.getLogger(Ethereum.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    
    }
    
}
