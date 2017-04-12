/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zentrale;

import java.net.*;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.BufferedReader;
/**
 *
 * @author alex
 */
public class SensorConnection extends Thread {
    Socket _client;
    public SensorConnection(Socket client) {
        this._client = client;
    }
    public void run() {
        String clientAnswer;
        BufferedReader fromClient;
        DataOutputStream toClient;
        boolean connected = true;
        System.out.println("connected to Client" + _client.getInetAddress());
        try {
            fromClient = new BufferedReader(new InputStreamReader(_client.getInputStream()));
            toClient = new DataOutputStream(_client.getOutputStream());
            while(connected) {
                clientAnswer = fromClient.readLine();
                System.out.println("Received: " + clientAnswer);
                
            }
        } catch (Exception e) {}
    }
}
