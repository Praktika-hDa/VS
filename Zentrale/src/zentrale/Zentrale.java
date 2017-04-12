/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zentrale;

import java.net.*;
/**
 *
 * @author alex
 */
public class Zentrale {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int port = 4444;
        try{
        
        ServerSocket listenSocket = new ServerSocket(port);
        while(true)
        {
            Socket client = listenSocket.accept();
            System.out.println("Incoming Connection:" + client.getRemoteSocketAddress());
            new SensorConnection(client).start();
        }
        } catch(Exception e) {}
    }
    
}
