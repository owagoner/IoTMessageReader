package edu.cmu.iotmessagereader;

import edu.cmu.web3j.SensorGroup;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

/**
 *
 * @author Owen Wagoner - Carnegie Mellon University - Heinz College
 */
public class Ethereum {

    private String endpointUrl = "http://localhost:8086/";

    private static Credentials credentials;
    private static Web3j web3;
    private static SensorGroup contract;

    public Ethereum(String contractAddress, String pathToWalletFile, String password) {

        web3 = Web3j.build(new HttpService(endpointUrl));
        try {
            credentials = WalletUtils.loadCredentials(password, pathToWalletFile);

            BigInteger gasPrice = new BigInteger("200000000");
            BigInteger gasLimit = new BigInteger("2000000000");

            contract = SensorGroup
                    .load(contractAddress, web3, credentials, gasPrice, gasLimit);

        } catch (IOException | CipherException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean addSensor(String hash, String pubKey) {
        try {

            Utf8String utf8Hash = new Utf8String(hash);
            Utf8String utf8PubKey = new Utf8String(pubKey);

            TransactionReceipt tr = contract.addSensor(utf8Hash, utf8PubKey).get();

            return true;

        } catch (InterruptedException | ExecutionException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean validateSensor(String sensorHash) {
        try {
            Utf8String utf8Hash = new Utf8String(sensorHash);
            Bool result = contract.validateHash(utf8Hash).get();
            return result.getValue();

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }

    }

}
