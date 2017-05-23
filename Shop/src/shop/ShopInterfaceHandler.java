/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;

/**
 *
 * @author alex
 */
public class ShopInterfaceHandler implements ShopInterface.Iface {
    private ShopInstance _shop;
    public ShopInterfaceHandler(ShopInstance shop) {
        _shop = shop;
    }
    
    public double getPrice(String product) throws TException {
        return _shop.getProductPrice(product);
    }

    public void Order(String product, int quantity, String ip, int port) throws TException {
       String message = product + " " + quantity + " ";
        try {
            Socket clientSocket = new Socket(ip, port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(message + "\n");
            clientSocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

  
}
