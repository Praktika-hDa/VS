/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zentrale;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author alex
 */
public class SimpleHttpServer {
    private static List<Sensor> _sensorList = new ArrayList();
    //Init the Webserver 
    public SimpleHttpServer() throws Exception {
        //Bind Server to Port
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        //Set Handler for access localhost:8000/test
        server.createContext("/test", new MyHttpHandler());
        server.setExecutor(null);
        //Start WebServer
        server.start();
    }
    public void setsensorList (List<Sensor> sensorList) {
        this._sensorList = sensorList;
    }
    //Webserver Handle for /test
    static class MyHttpHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            //Bild the Response Message
            byte [] message;
            //If no sensor Information available send if or else if available
            if (_sensorList.size() == 0) {
                message = "No Sensor Information!".getBytes();
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
                message = probeInfos.toString().getBytes();
            }
            //Send the message
            t.sendResponseHeaders(200, message.length);
            OutputStream os = t.getResponseBody();
            os.write(message);
            //Close connection
            os.close();
        }
    }
}
