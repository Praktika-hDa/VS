/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suppliermanager;

/**
 * Represent an offer which is send over MQTT to idetify if it's still valid
 * @author alex
 */
public class Angebot extends Thread {
    private int _angebotNbr;
    private int _timeout;
    private int _quantity;
    private float _price;
    private boolean _valid;
    public Angebot(int angebotNbr, int timeout, int quantity, float price) {
        _angebotNbr = angebotNbr;
        _timeout = timeout;
        _quantity = quantity;
        _price = price;
        _valid = true;
    }
    public String getAngebot() {
        return _angebotNbr + " " + _quantity + " " + _price + " ";
    }
    public boolean getValid() {
        return _valid;
    }
    public int getQuantity() {
        return _quantity;
    }
    public float getPrice() {
        return _price;
    }
    public void Run() throws InterruptedException {
        while(_timeout != 0) {
            _timeout--;
            Thread.sleep(1000);
        }
    }
}
