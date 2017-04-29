/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zentrale;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alex
 */
//TCP HTTP Server
public class TCPWebserver implements Runnable {
    private ServerSocket _webserver;
    private List<Sensor> _sensorList = new ArrayList();
    //Start the TCP Server
    public TCPWebserver() {
        try{
        _webserver = new ServerSocket(2222);
        } catch (Exception e) {
                System.out.println(e);
        }
    }
    public void setSensorList(List<Sensor> sensorList) {
        this._sensorList = sensorList;
    }
    //Task which listen for incoming Connection on this Port
    public void run() {
        while(true) {
            try {
                //Accept connection and move it to a new separated thread
                Socket client = _webserver.accept();
                TCPConnection incomingConnection = new TCPConnection(client);
                incomingConnection.start();
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }
    //Class which represent an incomming Client Connection
    private class TCPConnection extends Thread {
        private Socket _client;
        private BufferedReader _incomingMessage;
        private DataOutputStream _outgoingMessage;
        private String _message;
        private String[] _splitedRequest;
        
        public TCPConnection(Socket client) {
            this._client = client;
        }
        public void run() {
            try {
                //Initiallize the Streams
                _incomingMessage = new BufferedReader(new InputStreamReader(_client.getInputStream()));
                _outgoingMessage = new DataOutputStream(_client.getOutputStream());
                //Read Request
                _message = _incomingMessage.readLine();
                //Split the Request
                _splitedRequest = _message.split(" ");
                if ("/".equals(_splitedRequest[1].trim())) {
                    _outgoingMessage.writeBytes(createProbeInfo());
                }
                else {
                    _outgoingMessage.writeBytes("Error: URL not found!");
                }
                System.out.println(_message);
                _outgoingMessage.close();
            } catch(Exception e) {
            
            }
        }
        //Create the String Message with the Probe information
        private String createProbeInfo() {
            String message;
            //If no sensor Information available send if or else if available
            if (_sensorList.size() == 0) {
                message = "No Sensor Information!";
            } else {
                List<String> probeInfos = new ArrayList();
                //First loop for going throught all Probes
                for (int i = 0; i < _sensorList.size(); i++) {
                    String fillingHistory = new String();
                    //Second loop for going throught the selescted Probe filling history
                    for (int k = 0; k < _sensorList.get(i).getpastFillings().size(); k++) {
                        fillingHistory += _sensorList.get(i).getpastFillings().get(k).toString() + "; ";
                    }
                    //Build the Probe Information String
                    String probeInfo = "\n Probe Type: " + _sensorList.get(i).getsensorTyp() 
                            + "\n Current remaining filling: " + _sensorList.get(i).getcurrentfilling()
                            + "\n Fill History: " + fillingHistory;
                    probeInfos.add(probeInfo);
                }
                //Convert the Array of all Probe information string into Byte
                message = probeInfos.toString();
            }
            return message;
        }
    }
}
