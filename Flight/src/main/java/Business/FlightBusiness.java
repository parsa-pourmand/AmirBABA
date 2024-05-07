/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 *
 * @author student
 */
import Helper.Flight;
import Persistence.Flight_CRUD;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;

/**
 *
 * @author amir
 */
public class FlightBusiness {
    
    public ArrayList<Flight> getflights() {
        
        Flight_CRUD flightCRUD = new Flight_CRUD();
        ArrayList<Flight> allFlights = new ArrayList<>();
        try {
            allFlights = flightCRUD.readall();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(FlightBusiness.class.getName()).log(Level.SEVERE, null, e);
        }
        return allFlights;
    }
    
    public ArrayList<Flight> getAirlineFlights(String username){
        
        Flight_CRUD flightCRUD = new Flight_CRUD();
        ArrayList<Flight> AirlineFlights = new ArrayList<>();
        try{
            AirlineFlights = flightCRUD.read(username);    
        } catch (ClassNotFoundException e){
             Logger.getLogger(FlightBusiness.class.getName()).log(Level.SEVERE, null, e);
        }
        return AirlineFlights;
    }
    
    public boolean addFlight(Flight flight, String username) throws ClassNotFoundException{
        boolean status = false;
        Flight_CRUD flightCRUD = new Flight_CRUD();
        status = flightCRUD.create(flight, username);
        try {
            if(status){
                Messaging.sendmessage("Flight:" + flight.getFlightNumber() + ":" + flight.getPrice() + ":" + flight.getOrigin() + ":" + flight.getDestination() + ":" +flight.getAirplaneType() + ":"+ username);    
            }
        }
        catch (IOException e) {
        // Handle the exception here, perhaps logging it or informing the user
        e.printStackTrace();
        }
        return status;
    }
   
}

