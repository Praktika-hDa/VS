/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author alex
 */
public class ShopInstance {
    static List<Product> _products = new ArrayList<Product>();
    private MqttInstance _mqtt;
    public ShopInstance() throws MqttException {
        _mqtt = new MqttInstance("tcp://localhost");
    }
    public void AddProduct(String name, float price) {
    Product newProduct = new Product();
    System.out.println(name + " " + price);
    newProduct.Name = name;
    newProduct.Price = price;
    newProduct.Quantity = 50;
    _products.add(newProduct);
    }
    public float getProductPrice(String name) {
        for (int i = 0; i < _products.size(); i++) {
            if(_products.get(i).Name.equals(name)) {
                return _products.get(i).Price;
            }
        }
        return 9999;
    }
    public void Order(String name, int quantity) throws MqttException {
        for (int i = 0; i < _products.size(); i++) {
            if(_products.get(i).Name.equals(name)) {
                _products.get(i).Quantity -= quantity;
                if (_products.get(i).Quantity <= 30) {
                _products.get(i).Refill = true;
                _mqtt.mqttPublish("/Nachfrage/"+ name, "");
                }
            }
        }
    }
}
