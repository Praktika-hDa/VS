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
        private String HTTP_HEADER = "Content-Type: text/html; charst=UT-8\r\n" + "Content-Lenght: ";
        private String HTTP_HEADER_END = "\r\n\r\n";
        
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
                else if("/status".equals(_splitedRequest[1].trim())) {
                    _outgoingMessage.writeBytes(createProbeInfo());
                }
                else {
                    String OUTPUT = "<html><title>Not Found!</title><body><p>URL: " + _splitedRequest[1] + " not found</p></body></html>";
                    _outgoingMessage.writeBytes("HTTP/1.1 404 Not Found\r\n" + HTTP_HEADER + OUTPUT.length() + HTTP_HEADER_END + OUTPUT);
                   _outgoingMessage.flush();
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
                String OUTPUT = "<html><title>Status</title><body><p>No Sensor Information!</p></body></html>";
                message = "HTTP/1.1 200 OK\r\n"+ HTTP_HEADER + OUTPUT.length() + HTTP_HEADER_END + OUTPUT;
            } else {
                String OUTPUT = "<html><title>Status</title><body>";
                //First loop for going throught all Probes
                for (int i = 0; i < _sensorList.size(); i++) {
                    String fillingHistory = new String();
                    //Second loop for going throught the selescted Probe filling history
                    for (int k = 0; k < _sensorList.get(i).getpastFillings().size(); k++) {
                        fillingHistory += _sensorList.get(i).getpastFillings().get(k).toString() + "; ";
                    }
                    //Build the Probe Information HTML String
                    OUTPUT += "<p><b>Probe Type: " + _sensorList.get(i).getsensorTyp() + "</b></p>";                    
                    OUTPUT += "<p>Current remaining filling: " + _sensorList.get(i).getcurrentfilling() + "</p>";
                    OUTPUT += "<p>Fill History: " + fillingHistory + "</p>";
                }
                OUTPUT += "</body></html>";
                message = "HTTP/1.1 200 OK\r\n" + HTTP_HEADER + OUTPUT.length() + HTTP_HEADER_END + OUTPUT;
            }
            return message;
        }
    }
}
