/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.iotmessagereader;

import java.io.IOException;
import java.util.ArrayList;

public class MessageReaderApp {

    public static void main(String[] args) throws IOException, InterruptedException {

        while (true) {
            ReadQueue rq = new ReadQueue();
            ArrayList<String> list = rq.ReadMessage();

            for (String s : list) {
                System.out.println(s);
            }
            Thread.sleep(5000);
        }

    }
}
