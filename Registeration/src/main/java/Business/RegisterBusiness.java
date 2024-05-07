/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.logging.Level;
import java.util.logging.Logger;
import Helper.Passenger;
import Helper.Airline;
import Persistence.Airline_CRUD;
import Persistence.Passenger_CRUD;
import java.io.IOException;

/**
 *
 * @author student
 */

public class RegisterBusiness {
    
    public boolean addPassenger(Passenger pass){
        Passenger_CRUD passengerCRUD = new Passenger_CRUD();
        boolean status = passengerCRUD.creat(pass);
        try{
            if(status){
                Messaging.sendmessage("Passenger:" + pass.getFirstname() + ":" + pass.getLastname() + ":" + pass.getEmail() + ":" + pass.getPassword() + ":" + 
                    pass.getUsername());
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return status; 
    }
    
    public boolean addAirline(Airline airline){
        Airline_CRUD airlineCRUD = new Airline_CRUD();
        boolean status = airlineCRUD.creat(airline);
        try{
            if(status){
                Messaging.sendmessage("Airline:" + airline.getName() + ":" + airline.getEmail() + ":" + airline.getUsername() + ":"
                    + airline.getPassword() + ":" + airline.getPhoneNumber());
            }
        }catch (IOException e) {
        
            e.printStackTrace();
        }
        return status;
    }
    
}
