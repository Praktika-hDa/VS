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
    //private List _probeList = new ArrayList();
    private List<Sensor> _probeList = new ArrayList<Sensor>();
    private TCPWebserver _webServer;
    public SensorConnection(int n, TCPWebserver server) {
        this._webServer = server;
        for (int i = 0; i < n; i++) {
            Sensor mySensor = new Sensor(i+1);
            _probeList.add(mySensor);
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
                String _probeTyp = splitedMessage[2];
                //Update Collection
                System.out.println("Anfang Sensor: " + _probeNumber + " " + splitedMessage[2] + " Value: " + _probeList.get(_probeNumber-1).getcurrentfilling());
                _probeList.get(_probeNumber - 1).setcurrentFilling(_probeRemainingQuantity);
                _probeList.get(_probeNumber - 1).setsensorTyp(_probeTyp);
                _webServer.setSensorList(_probeList);
                System.out.println("Ende Sensor: " + _probeNumber + " " + splitedMessage[2] + " Value: " + _probeList.get(_probeNumber-1).getcurrentfilling());
            }
        } catch(Exception e) 
        {
            System.out.println(e);
        }
    }
}
