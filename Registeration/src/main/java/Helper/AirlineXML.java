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
@XmlRootElement(name = "airline")

public class AirlineXML {
    private Airline airline;

    public AirlineXML() {} // JAXB requires a no-arg constructor for unmarshalling

    public AirlineXML(Airline airline) {
        this.airline = airline;
    }

    @XmlElement(name = "airline")
    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
