/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorenmanager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 *
 * @author alex
 */
public class SensorenManager {

    /**
     * @param args the command line arguments
     */
    private static List<Sensore> _sensoreList = new ArrayList<Sensore>();
    public static void main(String[] args) {
        String[] _foodType = new String[] {"Butter","Brot","Salami","Milch","Jever" };
        // TODO code application logic here
        for (int i = 1; i < 6; i++) {
            Random rand = new Random();
            Sensore mySensore = new Sensore(rand.nextInt(50)+1,i,_foodType[i-1]);
            mySensore.start();
            _sensoreList.add(mySensore);
        }
        TCPRefillServer TCPServer = new TCPRefillServer(_sensoreList);
        TCPServer.start();
    }
    
}
