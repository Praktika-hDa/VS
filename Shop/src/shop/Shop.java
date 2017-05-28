/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author alex
 */
public class Shop {

    static ShopInstance _shop;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String[] _foodType = new String[] {"Butter","Brot","Salami","Milch","Jever" };
            _shop = new ShopInstance();
            for (int i = 0; i < 5; i++) {
                float leftLimit = 1F;
                float rightLimit = 10F;
                float randomFloat = leftLimit + new Random().nextFloat() * (rightLimit - leftLimit);
                _shop.AddProduct(_foodType[i], randomFloat);
            }
            ShopInterfaceServer server = new ShopInterfaceServer(_shop);
        } catch (MqttException ex) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
