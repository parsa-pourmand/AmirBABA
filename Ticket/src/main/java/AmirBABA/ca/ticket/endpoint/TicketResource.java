/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AmirBABA.ca.ticket.endpoint;

import AmirBABA.ca.ticket.business.TicketBusiness;
import AmirBABA.ca.ticket.helper.Ticket;
import AmirBABA.ca.ticket.helper.Tickets;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *REST Web Service
 * @author amir
 */

@Path("Ticket")
public class TicketResource {
    
    @Context
    private HttpHeaders httpHeaders; 
    
    @Context
    private UriInfo context;
    
    public TicketResource(){
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("Passenger/{username}")
    public String getXmlTicket(@PathParam("username") String username){
        TicketBusiness ticketbusiness = new TicketBusiness();
        ArrayList<Ticket> passengerTickets = ticketbusiness.getTickets(username);
        if (passengerTickets == null || passengerTickets.isEmpty()) {
            // Returning an XML-formatted message when there's no data
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response>The list of flights is empty or could not be retrieved.</response>";
        }
        
        try {
        JAXBContext jaxbContext = JAXBContext.newInstance(Tickets.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        
        Tickets ticketsWrapper = new Tickets(passengerTickets); // Adjusted to use the corrected Tickets class
        
        jaxbMarshaller.marshal(ticketsWrapper, sw);

        return sw.toString();

        } catch (JAXBException ex) {
        Logger.getLogger(TicketResource.class.getName()).log(Level.SEVERE, null, ex);
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><error>An error occurred while processing your request.</error>";
        }        
    }
    
    
    
@POST
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_PLAIN)
@Path("AddTicket")
public String addTicket(@FormParam("flightNumber") int flightNumber,
                        @FormParam("TicketNumber") int TicketNumber,
                        @FormParam("firstName") String firstName,
                        @FormParam("lastName") String lastName,
                        @FormParam("email") String email,
                        @FormParam("username") String username) {

    TicketBusiness ticketBusiness = new TicketBusiness();
    Ticket ticket = new Ticket(TicketNumber);
    ticket.setFlightNum(flightNumber);
    ticket.setFirstname(firstName);
    ticket.setLastname(lastName);
    ticket.setEmail(email);
    ticket.setUsername(username);
    
    boolean result = ticketBusiness.addTicket(ticket);
    if (result) {
        return "Ticket successfully added";
    } else {
        return "Failed to add ticket";
    }
}
}
