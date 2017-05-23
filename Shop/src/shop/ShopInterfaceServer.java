/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import org.apache.thrift.TException;
import org.apache.thrift.server.THsHaServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 *
 * @author alex
 */
public class ShopInterfaceServer {
    
    private ShopInterfaceHandler _handler;
    private ShopInterface.Processor _processor;
    
    public ShopInterfaceServer(ShopInstance shop) {
        try {
            _handler = new ShopInterfaceHandler(shop);
            _processor = new ShopInterface.Processor(_handler);
            
            Runnable server = new Runnable() {
                public void run() {
                    startServer(_processor);
                }
            };
            
            new Thread(server).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startServer(ShopInterface.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            
            System.out.println("Starte Server");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

