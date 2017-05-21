/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zentrale;

import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
/**
 *
 * @author alex
 */
public class Shop  {
    private ShopInterface.Client _client;
    public Shop(String ip, int port) {
        try {
        //Assign Socket
        TTransport transport = new TSocket(ip, port);
        //Start Socket
        transport.open();
        
        TProtocol protocol = new TBinaryProtocol(transport);
        
        _client = new ShopInterface.Client(protocol);
        
        } catch(TException x) {
            x.printStackTrace();
        }
    }
    public double getPrice(String Probe) throws TException {
        return _client.getPrice(Probe);
    }

    public void Order(String product, int quantity, String ip, int port) throws TException {
        _client.Order(product, quantity, ip, port);
    }
}
