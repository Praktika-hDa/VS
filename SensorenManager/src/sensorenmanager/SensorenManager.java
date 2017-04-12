/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorenmanager;
import java.util.Random;
/**
 *
 * @author alex
 */
public class SensorenManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        for (int i = 1; i < 3; i++) {
            Random rand = new Random();
            Sensore mySensore = new Sensore(rand.nextInt(50)+1,i);
            mySensore.start();
        }
    }
    
}
