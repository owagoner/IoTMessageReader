/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.sensormessage;

import com.google.gson.Gson;
import java.util.Date;

/**
 *
 * @author owagoner
 */
public class SensorReadingMessage {
    
    private String sensorHash;
    private String data;
    private Date date;
    private String signature;

    public SensorReadingMessage(){
    
    }
    
    public SensorReadingMessage(String sensorHash, String data, Date date) {
        this.sensorHash = sensorHash;
        this.data = data;
        this.date = date;
    }

    public String getSensorHash() {
        return sensorHash;
    }

    public void setSensorHash(String sensorHash) {
        this.sensorHash = sensorHash;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    public String serialize(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
