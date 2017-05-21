/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zentrale;
import java.util.List;
import java.util.ArrayList;
import org.apache.thrift.TException;

/**
 *
 * @author alex
 */
public class Sensor {
    private int _sensorNbr;
    private String _sensorTyp;
    private int _currentFilling;
    private List _pastFillings;
    private List<Shop> _shops;
    
    private String _ip;
    private int port = 4444;
    
    public int getsensorNbr() {
        return _sensorNbr;
    }
    public void setsensorTyp(String typ) {
        this._sensorTyp = typ;
    }
    public String getsensorTyp() {
        return _sensorTyp;
    }
    public void setcurrentFilling(int fill) {
        this._currentFilling = fill;
        if (_currentFilling == 5) {
            try {
            Refill();
            } catch (TException e) {
                e.printStackTrace();
            }
        }
        this._pastFillings.add(0, fill);
    }
    public int getcurrentfilling() {
        return _currentFilling;
    }
    public List getpastFillings() {
        return _pastFillings;
    }
    public void setIp(String ip) {
        _ip = ip;
    }
    public Sensor(int Nbr, List<Shop> shops) {
        this._sensorNbr = Nbr;
        this._pastFillings = new ArrayList();
        this._shops = shops;
    }
    
    private void Refill() throws TException {
        double cheapest = 0;
        int cheapestShop = 0;
        for (int i = 0; i < _shops.size(); i++) {
            double tmp = _shops.get(i).getPrice(_sensorTyp);
            
            if (cheapest == 0) {
                 cheapest = tmp;
                 cheapestShop = i;
            }
            else if (tmp < cheapest) {
                 cheapest = tmp;
                 cheapestShop = i;
            }
        }
        _shops.get(cheapestShop).Order(_sensorTyp, 15, _ip, port);
    }
}
