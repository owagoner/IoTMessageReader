package edu.cmu.iotmessagereader;

import java.io.IOException;

public class MessageReaderApp {

    public static void main(String[] args) throws IOException, InterruptedException {
        String contractAddress = args[0];
        String pathToWalletFile = "/Users/owenwagoner/ethereum/.ethereum_private/keystore/UTC--2017-04-19T21-12-19.768360257Z--224ef353337635da0c626f247e0f78719eeee205";
        String password = "password1";
        
        System.out.println("Contract at: " + contractAddress);
        System.out.println("Connecting to Azure service bus...");
        while (true) {
            ReadQueue rq = new ReadQueue(contractAddress, pathToWalletFile, password);
            rq.ReadMessages();
            //Wait for more messages.
            Thread.sleep(5000);
        }

    }
}
