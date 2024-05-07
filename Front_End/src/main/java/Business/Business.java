package Business;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.ws.rs.client.Entity;
import org.apache.commons.io.IOUtils;
import Helper.Flight;
import Helper.FlightXML;
import Helper.Airline;
import Helper.Ticket;
import Helper.TicketXML;
import Helper.Passenger;
import Persistence.Airline_CRUD;
import Persistence.Passenger_CRUD;
import Persistence.Flight_CRUD;
import Persistence.Ticket_CRUD;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.Marshaller;
import javax.ws.rs.core.Form;
import java.util.ArrayList;


/**
 *
 * @author student
 */
public class Business {

    public static boolean Airline_Authentication(String username, String password) {
        boolean result;
        Airline airline = new Airline(username, password);
        result = Airline_CRUD.checkLogin(airline);
        return result;
    }
    
    public static boolean Passenger_Authentication(String username, String password) {
        boolean result;
        Passenger passenger = new Passenger(username, password);
        result = Passenger_CRUD.checkLogin(passenger);
        
        return result;
    }

    public static ArrayList<Flight> RetreiveFlights(String usernameQ, String token) throws IOException {
        
        ArrayList<Flight> theflights = new ArrayList<Flight>();
        Flight_CRUD flight_crud = new Flight_CRUD();
        try {
            if(usernameQ == null || usernameQ.isEmpty()){
            
            theflights = flight_crud.readall();
            System.out.println(theflights.size());

            return (theflights);    
            } else{
                theflights = flight_crud.read(usernameQ);
        
                return (theflights);
            }
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Business.class.getName()).log(Level.SEVERE, null, e);
        }
        return theflights;
    }
    
    public static ArrayList<Ticket> RetreiveTickets(String query, String token) throws IOException {
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        Ticket_CRUD ticket_crud = new Ticket_CRUD();
        try{
            
            tickets = ticket_crud.read(query);
        
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Business.class.getName()).log(Level.SEVERE, null, e);
        }
        return tickets;
    }
    
    public static boolean SendFlight(Flight flightxml, String token) {
        try {
            
            Client client = ClientBuilder.newClient(); 
            String flightService= System.getenv("flightService");
            WebTarget webTarget = client.target("http://"+ flightService +"/Flight/webresources/Flights/Addflight");

            Form form = new Form();
            form.param("flightNumber", String.valueOf(flightxml.getFlightNumber()));
            form.param("price", String.valueOf(flightxml.getPrice()));
            form.param("origin", flightxml.getOrigin());
            form.param("destination", flightxml.getDestination());
            form.param("airplaneType", flightxml.getairplane());
            
            Response response = webTarget.request(MediaType.APPLICATION_FORM_URLENCODED)
            .header("Accept", MediaType.TEXT_PLAIN) // Explicitly accept TEXT_PLAIN responses
            .header("Authorization", "Bearer " + token)
            .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
            
            int statusCode = response.getStatus();
            String responseBody = response.readEntity(String.class);
            
            
            return statusCode >= 200 && statusCode < 300;                 

            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
    }

    public static boolean SendTicket(Ticket Ticketxml, String token) {
        try {
            Client client = ClientBuilder.newClient();
            String ticketService= System.getenv("ticketService");
            WebTarget webTarget = client.target("http://" + ticketService + "/Ticket/webresources/Ticket/AddTicket");

            Form form = new Form();
            form.param("flightNumber", String.valueOf(Ticketxml.getFlightNum()));
            form.param("TicketNumber", String.valueOf(Ticketxml.getTicketNumber()));
            form.param("firstName", Ticketxml.getFirstname());
            form.param("lastName", Ticketxml.getLastname());
            form.param("email", Ticketxml.getEmail());
            form.param("username", token);
            

            Response response = webTarget.request(MediaType.APPLICATION_FORM_URLENCODED)
            .header("Accept", MediaType.TEXT_PLAIN) // Explicitly accept TEXT_PLAIN responses
            .header("Authorization", "Bearer " + token)
            .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

            return response.getStatus() == Response.Status.OK.getStatusCode();

            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
    }
    
    public static boolean AddAirline(Airline airline){
        try {
            Client client = ClientBuilder.newClient();
            
            String registerService= System.getenv("registerService");
            WebTarget webTarget = client.target("http://" + registerService + "/Registeration/webresources/Register/AddAirline");

            Form form = new Form();
            form.param("username", airline.getUsername());
            form.param("password", airline.getPassword());
            form.param("name", airline.getName());
            form.param("phone", String.valueOf(airline.getPhoneNumber()));
            form.param("email", airline.getEmail());

            Response response = webTarget.request(MediaType.APPLICATION_FORM_URLENCODED)
            .header("Accept", MediaType.TEXT_PLAIN) // Explicitly accept TEXT_PLAIN responses
            .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

            return response.getStatus() == Response.Status.OK.getStatusCode();

            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
    }
    
    public static boolean AddPassenger(Passenger passenger){
        
        try {
            Client client = ClientBuilder.newClient();
            String registerService= System.getenv("registerService");
            WebTarget webTarget = client.target("http://" + registerService + "/Registeration/webresources/Register/AddPassenger");

            Form form = new Form();
            form.param("username", passenger.getUsername());
            form.param("password", passenger.getPassword());
            form.param("firstName", passenger.getFirstname());
            form.param("lastName", passenger.getLastname());
            form.param("email", passenger.getEmail());

            Response response = webTarget.request(MediaType.APPLICATION_FORM_URLENCODED)
            .header("Accept", MediaType.TEXT_PLAIN) // Explicitly accept TEXT_PLAIN responses
            .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

            return response.getStatus() == Response.Status.OK.getStatusCode();

            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
    }
    
    private static FlightXML flightxmltoObjects(String xml) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(FlightXML.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            FlightXML flights = (FlightXML) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return flights;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static TicketXML TicketxmltoObjects(String xml) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(TicketXML.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            TicketXML tickets = (TicketXML) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return tickets;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
    
