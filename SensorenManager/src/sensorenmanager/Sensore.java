/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorenmanager;

import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
/**
 *
 * @author alex
 */
public class Sensore extends Thread {
    
    //Probe variables
    private int _remainingQuantity;
    private int _probeNumber;
    //Connection variables
    private static Socket _socket;
    private static DataOutputStream _toServer;
    private static BufferedReader _fromServer;
    
    public void run() {
        try {
        //Init Connection
        _socket = new Socket("localhost", 4444);
        _toServer = new DataOutputStream(_socket.getOutputStream());
        _fromServer = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
        
        } catch (Exception e) {}
        
        try {
            while(_remainingQuantity > 0)
            {
                Thread.sleep(500);
                _remainingQuantity--;
                System.out.println("Sensor: " + _probeNumber + "; Remaining Filling: " + _remainingQuantity);
                sendMessage();
            }
        } catch (InterruptedException e) {}
    }
    public Sensore(int remainingQuantity, int probeNumber) {
    this._remainingQuantity = remainingQuantity;
    this._probeNumber = probeNumber;
    }
    //send a Message to the Server
    //8 byte Array 0-3 ProbeNumber 4-7 RemainingQuantity
    private void sendMessage() {
        //Need to Convert the Int into a byte Array
        List<Byte> message = new ArrayList<Byte>();
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(_probeNumber);
        byte[] result = b.array();
        for (int i = 0; i < 4; i++) {
            message.add(result[i]);
        }
        b.clear();
        b = ByteBuffer.allocate(4);
        b.putInt(_remainingQuantity);
        result = b.array();
        for (int i = 0; i < 4; i++) {
            message.add(result[i]);
        }
        //Convert Byte List to byte[]
        result = new byte[10];
        for (int i = 0; i < message.size(); i++) {
            result[i] = message.get(i);
        }
        result[8] = 0x2F;
        result[9] = 0x6E;
        try{
            _toServer.write(result);
        } catch (IOException e) {}
    }
}
