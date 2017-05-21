/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zentrale;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author alex
 */
public class Zentrale {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        
       List<Shop> _shops = new ArrayList<Shop>();
        
        try{
            
            Shop shop1 = new Shop("localhost", 9090);
            _shops.add(shop1);
            System.out.println(shop1.getPrice("Jever"));
            TCPWebserver _tcpServer = new TCPWebserver();
            SensorConnection _sensoren = new SensorConnection(5, _tcpServer, _shops);
            
            new Thread(_tcpServer).start();
            _sensoren.start();
        }
        catch (Exception e) {
        
        }
    }
    
}
