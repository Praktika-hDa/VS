/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zentrale;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author alex
 */
public class Sensor {
    private int _sensorNbr;
    private String _sensorTyp;
    private int _currentFilling;
    private List _pastFillings;
    
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
        this._pastFillings.add(0, fill);
    }
    public int getcurrentfilling() {
        return _currentFilling;
    }
    public List getpastFillings() {
        return _pastFillings;
    }
    
    public Sensor(int Nbr) {
        this._sensorNbr = Nbr;
        this._pastFillings = new ArrayList();
    }
}
