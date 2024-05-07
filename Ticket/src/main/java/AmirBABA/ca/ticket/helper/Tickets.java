package AmirBABA.ca.ticket.helper;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tickets")
public class Tickets {

    private List<Ticket> tickets;

    public Tickets() {} // JAXB requires a no-arg constructor for unmarshalling

    public Tickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @XmlElement(name = "ticket")
    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
