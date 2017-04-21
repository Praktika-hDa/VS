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
        try{
            SimpleHttpServer webServer = new SimpleHttpServer();
            SensorConnection _sensoren = new SensorConnection(5, webServer);
            _sensoren.start();
        }
        catch (Exception e) {
        
        }
    }
    
}
