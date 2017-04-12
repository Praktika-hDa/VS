/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zentrale;

import java.net.*;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author alex
 */
public class SensorConnection extends Thread {
    private List _probeList = new ArrayList();
    public SensorConnection(int n) {
        for (int i = 0; i < n; i++) {
            _probeList.add(0);
        }
    }
    public void run() {
        try{
            //Connection Init
            DatagramSocket _serverSocket = new DatagramSocket(4444);
            byte[] _receivedData = new byte[1024];
            //Main Connection loop
            while(true)
            {
                //Receive new Package
                DatagramPacket _receivedPacket = new DatagramPacket(_receivedData, _receivedData.length);
                _serverSocket.receive(_receivedPacket);
                //Format String Message into right pieces
                String message = new String(_receivedPacket.getData());
                String[] splitedMessage = message.split("/");
                //Convert into Int
                int _probeNumber = Integer.parseInt(splitedMessage[0]);
                int _probeRemainingQuantity = Integer.parseInt(splitedMessage[1].trim());
                //Update Collection
                System.out.println("Anfang Sensor: " + _probeNumber + " Value: " + _probeList.get(_probeNumber-1));
                _probeList.set(_probeNumber-1, _probeRemainingQuantity);
                System.out.println("Ende Sensor: " + _probeNumber + " Value: " + _probeList.get(_probeNumber-1));
            }
        } catch(Exception e) 
        {
            System.out.println(e);
        }
    }
}
