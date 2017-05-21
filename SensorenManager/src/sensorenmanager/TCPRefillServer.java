/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorenmanager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alex
 */
public class TCPRefillServer extends Thread {
    private ServerSocket _webserver;
    private List<Sensore> _sensorList = new ArrayList();
    //Start the TCP Server
    public TCPRefillServer(List<Sensore> sensorList) {
        this._sensorList = sensorList;
        try{
        _webserver = new ServerSocket(4444);
        } catch (Exception e) {
                System.out.println(e);
        }
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
                for (int i = 0; i < _sensorList.size(); i++) {
                    if (_sensorList.get(i).getTyp().equals(_splitedRequest[0])) {
                        _sensorList.get(i).refill(Integer.parseInt(_splitedRequest[1]));
                    }
                }
                _outgoingMessage.close();
            } catch(Exception e) {
            
            }
        }
        
    }
}
