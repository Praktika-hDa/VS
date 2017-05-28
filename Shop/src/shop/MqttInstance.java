/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import static org.eclipse.paho.client.mqttv3.MqttAsyncClient.generateClientId;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author alex
 */
public class MqttInstance {
    private String _mqttBroker;
    String[] _topicList;
    public MqttInstance(String mqttBroker) throws MqttException {
        _mqttBroker = mqttBroker;
        _topicList = new String[]{"/Angebote/Butter", "/Angebote/Brot", "/Angebote/Salami", "/Angebote/Milch", "/Angebote/Jever"};
        MqttSubscribe();
        System.out.println("Shop MQTT subscibed");
    }
    //Subscribe to the right Channel for this supplier
    private void MqttSubscribe() throws MqttException {
        MqttClient client = new MqttClient(_mqttBroker, generateClientId());
        
        client.setCallback(new MqttCallback()  {
            @Override
            public void messageArrived(String t, MqttMessage m) throws Exception { 
                String[] splitedMessage = m.getPayload().toString().split(" ");
                if (_topicList[0].equals(t)) {
                    if (ShopInstance._products.get(0).Refill) {
                        mqttPublish("/Bestellung/" + _topicList[0], splitedMessage[0]);
                    }
                }
                else if (_topicList[1].equals(t)) {
                    if (ShopInstance._products.get(1).Refill) {
                        mqttPublish("/Bestellung/" + _topicList[1], splitedMessage[0]);
                    }
                }
                
                else if (_topicList[2].equals(t)) {
                    if (ShopInstance._products.get(2).Refill) {
                        mqttPublish("/Bestellung/" + _topicList[2], splitedMessage[0]);
                    }
                }
                else if (_topicList[3].equals(t)) {
                    if (ShopInstance._products.get(3).Refill) {
                        mqttPublish("/Bestellung/" + _topicList[3], splitedMessage[0]);
                    }
                }
                else if (_topicList[4].equals(t)) {
                    if (ShopInstance._products.get(4).Refill) {
                        mqttPublish("/Bestellung/" + _topicList[4], splitedMessage[0]);
                    }
                }
            }

            @Override
            public void connectionLost(Throwable thrwbl) {
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
        });
        
        client.connect();
        client.subscribe(_topicList);
    };
    //Publish a message to a selected MQTT Topic
    public void mqttPublish(String topic, String message) throws MqttException {
        MqttClient client = new MqttClient(_mqttBroker, generateClientId());
        client.connect();
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        client.publish(topic, mqttMessage);
        client.disconnect();
        System.out.println(topic + " " + message);
    }
}
