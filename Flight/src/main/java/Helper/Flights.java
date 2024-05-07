/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

/**
 *
 * @author student
 */
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "flights")
public class Flights {
    private List<Flight> flights;

    public Flights() {} // JAXB requires a no-arg constructor for unmarshalling

    public Flights(List<Flight> flights) {
        this.flights = flights;
    }

    @XmlElement(name = "flight")
    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}

