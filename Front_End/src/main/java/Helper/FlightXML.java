package Helper;

/**
 *
 * @author student
 */
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

           
@XmlRootElement(name = "flights")
@XmlAccessorType (XmlAccessType.FIELD)  
public class FlightXML {
    
    @XmlElement(name="flight")
    private ArrayList<Flight> flights;
           
    public FlightXML(){
               
               
    }
    
    public List<Flight>getFlights(){
        return flights;
               
    }
    
    
    
    public void setFlight(Flight bs){
        flights.add(bs);
               
    }
}
