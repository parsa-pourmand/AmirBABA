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
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

           
@XmlRootElement(name = "tickets")
@XmlAccessorType (XmlAccessType.FIELD)  
public class TicketXML {
    
    @XmlElement(name="ticket")
    private ArrayList<Ticket> tickets;
           
           
    public List<Ticket>getTickets(){
        return tickets;
               
    }
    
    TicketXML(){
               
               
    }
    
    public void setTicket(ArrayList<Ticket> bs){
        tickets=bs;
               
    }
}
