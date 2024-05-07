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
import Business.RegisterBusiness;
import Helper.Passenger;
import Helper.PassengerXML;
import Helper.Airline;
import Helper.AirlineXML;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map.Entry;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author student
 */

@Path("Register")
public class RegisterResource {
    @Context
    private HttpHeaders httpHeaders; 
    
    @Context
    private UriInfo context;
    
    public RegisterResource(){}
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) // This assumes form data is being sent
    @Produces(MediaType.TEXT_PLAIN)
    @Path("AddPassenger")
    public String addPassenger(@FormParam("username") String username,
                            @FormParam("email") String email,
                            @FormParam("firstName") String firstName,
                            @FormParam("lastName") String lastName,
                            @FormParam("password") String password) {
        try {
            
            Passenger newPassenger = new Passenger(username, password);
            newPassenger.setFirstname(firstName);
            newPassenger.setLastname(lastName);
            newPassenger.setEmail(email);

            
            RegisterBusiness registerBusiness = new RegisterBusiness();
            boolean isAdded = registerBusiness.addPassenger(newPassenger);
            return isAdded ? "Passenger added successfully." : "Failed to add the passenger.";
        } catch (SecurityException e) {
            Logger.getLogger(RegisterResource.class.getName()).log(Level.SEVERE, "Security issue", e);
            return "Authentication failed: " + e.getMessage();
        } catch (Exception e) {
            Logger.getLogger(RegisterResource.class.getName()).log(Level.SEVERE, "General error", e);
            return "An error occurred: " + e.getMessage();
        }
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) // This assumes form data is being sent
    @Produces(MediaType.TEXT_PLAIN)
    @Path("AddAirline")
    public String addAirline(@FormParam("username") String username,
                            @FormParam("email") String email,
                            @FormParam("name") String name,
                            @FormParam("phone") int phone,
                            @FormParam("password") String password) {
        try {
            
            Airline newAirline = new Airline(username, password);
            newAirline.setName(name);
            newAirline.setPhoneNumber(phone);
            newAirline.setEmail(email);

            
            RegisterBusiness registerBusiness = new RegisterBusiness();
            boolean isAdded = registerBusiness.addAirline(newAirline);
            return isAdded ? "Airline added successfully." : "Failed to add the airline.";
        } catch (SecurityException e) {
            Logger.getLogger(RegisterResource.class.getName()).log(Level.SEVERE, "Security issue", e);
            return "Authentication failed: " + e.getMessage();
        } catch (Exception e) {
            Logger.getLogger(RegisterResource.class.getName()).log(Level.SEVERE, "General error", e);
            return "An error occurred: " + e.getMessage();
        }
    }
}
