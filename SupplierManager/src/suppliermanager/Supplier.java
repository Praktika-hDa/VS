package suppliermanager;

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
import org.eclipse.paho.client.mqttv3.MqttTopic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alex
 */
public class Supplier implements Runnable {
    private String _typ;
    private String _mqttBroker;
    String[] _topicList;
    private List<Angebot> _angebotList;
    public Supplier(String typ, String mqttBroker) throws MqttException {
        _typ = typ;
        _mqttBroker = mqttBroker;
        _topicList = new String[]{"/Bestellung/"+_typ, "/Nachfrage/"+_typ};
        _angebotList = new ArrayList();
        MqttSubscribe();
        System.out.println("Supplier created " + _typ);
    }
    //Subscribe to the right Channel for this supplier
    private void MqttSubscribe() throws MqttException {
        MqttClient client = new MqttClient(_mqttBroker, generateClientId());
        
        client.setCallback(new MqttCallback()  {
            @Override
            public void messageArrived(String t, MqttMessage m) throws Exception { 
                if (_topicList[0].equals(t)) {
                    
                }
                else if (_topicList[1].equals(t)) {
                    Angebot offer;
                    int randomInt;
                        try {
                            //Generate random float for the price
                            float leftLimit = 1F;
                            float rightLimit = 10F;
                            float randomFloat = leftLimit + new Random().nextFloat() * (rightLimit - leftLimit);
                            randomInt = ThreadLocalRandom.current().nextInt(10, 100);
                            int randomqnt = ThreadLocalRandom.current().nextInt(1, 100);
                            offer = new Angebot(_angebotList.size()+1, randomInt, randomqnt, randomFloat);
                            _angebotList.add(offer);
                            mqttPublish("/Angebote/"+_typ, offer.getAngebot());
                            offer.start();
                        } catch (Exception ex) {
                            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
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
    //Task create new Special offers from time to time and publish them
    public void run() {
        Angebot offer;
        int randomInt;
        System.out.println("Supplier started " + _typ);
        while(true) {
            try {
                //Generate random float for the price
                float leftLimit = 1F;
                float rightLimit = 10F;
                float randomFloat = leftLimit + new Random().nextFloat() * (rightLimit - leftLimit);
                randomInt = ThreadLocalRandom.current().nextInt(10, 100);
                int randomqnt = ThreadLocalRandom.current().nextInt(1, 100);
                offer = new Angebot(_angebotList.size()+1, randomInt, randomqnt, randomFloat);
                _angebotList.add(offer);
                mqttPublish("/Angebote/"+_typ, offer.getAngebot());
                offer.start();
                Thread.sleep(randomInt * 1000 + 1000);
            } catch (Exception ex) {
                Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //Publish a message to a selected MQTT Topic
    private void mqttPublish(String topic, String message) throws MqttException {
        MqttClient client = new MqttClient(_mqttBroker, generateClientId());
        client.connect();
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        client.publish(topic, mqttMessage);
        client.disconnect();
        System.out.println(topic + " " + message);
    }

}
