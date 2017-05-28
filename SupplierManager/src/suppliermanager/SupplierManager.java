/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suppliermanager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author alex
 */
public class SupplierManager {

    /**
     * @param args the command line arguments
     */
    private static List<Supplier> _supplierList;
    public static void main(String[] args) {
        _supplierList = new ArrayList();
        String broker = "tcp://localhost";
        String[] _foodType = new String[] {"Butter","Brot","Salami","Milch","Jever" };
        for (int i = 1; i < 6; i++) {
            try {
                Supplier supplier = new Supplier(_foodType[i-1], broker);
                Thread tr = new Thread(supplier);
                tr.start();
                _supplierList.add(supplier);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
    
}
