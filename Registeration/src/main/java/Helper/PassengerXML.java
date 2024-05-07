/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author student
 */
@XmlRootElement(name = "passengers")

public class PassengerXML {
    private Passenger passenger;

    public PassengerXML() {} // JAXB requires a no-arg constructor for unmarshalling

    public PassengerXML(Passenger passenger) {
        this.passenger = passenger;
    }

    @XmlElement(name = "passenger")
    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger pass) {
        this.passenger = pass;
    }
}
