/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

/**
 *
 * @author student
 */

import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import Business.FlightBusiness;
import Helper.Flight;
import Helper.Flights;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map.Entry;
import javax.ws.rs.core.HttpHeaders;
import endpoint.Authenticate;
/**
 * REST Web Service
 * @author amir
 */
@Path("Flights")
public class FlightResource {
    
     
    
    @Context
    private UriInfo context;
    
    @Context
    private HttpHeaders httpHeaders;
    
    public FlightResource(){
        
    }
    
    // This method is implemented at path (passenger) to retrieve all the flights in the Database and send it to the fornt end microservice.
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("Passenger")
    public String getXMLFlight() {
        FlightBusiness flightbusiness = new FlightBusiness(); 
        ArrayList<Flight> flightList = flightbusiness.getflights();
        
        if (flightList == null || flightList.isEmpty()) {
            // Returning an XML-formatted message when there's no data
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response>The list of flights is empty or could not be retrieved.</response>";
        }
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Flights.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            
            Flights flightsWrapper = new Flights(flightList);
            jaxbMarshaller.marshal(flightsWrapper, sw);

            return sw.toString();

        } catch (JAXBException ex) {
            Logger.getLogger(FlightResource.class.getName()).log(Level.SEVERE, null, ex);
            // Returning an XML-formatted error message
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><error>An error occurred while processing your request.</error>";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("Airlines/{username}")
    public String getXMLAirlineFlight(@PathParam("username") String username){
        System.out.println(username);

        FlightBusiness flightbusiness = new FlightBusiness(); 
        ArrayList<Flight> AirlineFlights = flightbusiness.getAirlineFlights(username);
        if (AirlineFlights == null || AirlineFlights.isEmpty()) {
            // Returning an XML-formatted message when there's no data
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response>The list of flights is empty or could not be retrieved.</response>";
        }
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Flights.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            
            Flights flightsWrapper = new Flights(AirlineFlights);
            jaxbMarshaller.marshal(flightsWrapper, sw);

            return sw.toString();

        } catch (JAXBException ex) {
            Logger.getLogger(FlightResource.class.getName()).log(Level.SEVERE, null, ex);
            // Returning an XML-formatted error message
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><error>An error occurred while processing your request.</error>";
        }    
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) // This assumes form data is being sent
    @Produces(MediaType.TEXT_PLAIN)
    @Path("Addflight")
    public String addFlight(@FormParam("flightNumber") int flightNumber,
                            @FormParam("price") float price,
                            @FormParam("origin") String origin,
                            @FormParam("destination") String destination,
                            @FormParam("airplaneType") String airplaneType) {
        try {
            String airlineUsername = getAirlineUsername();
            Flight newFlight = new Flight(flightNumber, price, origin, destination, airplaneType);
            newFlight.setAirline(airlineUsername);
            
            FlightBusiness flightBusiness = new FlightBusiness();
            boolean isAdded = flightBusiness.addFlight(newFlight, airlineUsername);
            return isAdded ? "Flight added successfully." : "Failed to add the flight.";
        } catch (SecurityException e) {
            Logger.getLogger(FlightResource.class.getName()).log(Level.SEVERE, "Security issue", e);
            return "Authentication failed: " + e.getMessage();
        } catch (Exception e) {
            Logger.getLogger(FlightResource.class.getName()).log(Level.SEVERE, "General error", e);
            return "An error occurred: " + e.getMessage();
        }
    }

    
    
    private String getAirlineUsername() {
        String authorizationHeader = httpHeaders.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new SecurityException("No JWT token found in request headers");
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader.substring("Bearer ".length()).trim();

        // Instantiate Authenticate here, just before it's needed
        Authenticate autho = new Authenticate();

        // Verify the token
        Entry<Boolean, String> result;
        try {
            result = autho.verify(token);
            
        } catch (Exception e) {
            // Handle exceptions properly
            throw new SecurityException("Error verifying JWT token: " + e.getMessage(), e);
        }
        
        if (result.getKey()) { // If the token is valid
            return result.getValue(); // Return the username
        } else {
            throw new SecurityException("Invalid JWT token");
        }
    }
}

    


