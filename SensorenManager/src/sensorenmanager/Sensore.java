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
    private DatagramSocket _clientSocket;
    private InetAddress _ip;
    private byte[] _sendData = new byte[1024];
    private DatagramPacket _sendPackage;
    public void run() {
        try {
        //Init Connection
        _clientSocket = new DatagramSocket();
        _ip = InetAddress.getByName("localhost");
        
            while(_remainingQuantity > 0)
            {
                Thread.sleep(500);
                _remainingQuantity--;
                System.out.println("Sensor: " + _probeNumber + "; Remaining Filling: " + _remainingQuantity);
                _sendData = ("Sensor: " + _probeNumber + "; Remaining Filling: " + _remainingQuantity).getBytes();
                _sendPackage = new DatagramPacket(_sendData, _sendData.length, _ip, 4444);
                _clientSocket.send(_sendPackage);
                _clientSocket.close();
            }
        } catch (Exception e) {}
    }
    
    public Sensore(int remainingQuantity, int probeNumber) {
    this._remainingQuantity = remainingQuantity;
    this._probeNumber = probeNumber;
    }
}
