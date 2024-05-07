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
public class Flight {
    //Instance variables of class Flight
   private int flight_Number;
   private float price;
   private String origin, destination, airplane_type, airline;
    
   public Flight(){}
   
    public Flight(int flight_num, float price, String origin, String destination, String airplane){
            
        if(origin.equals(destination)) {
            throw new IllegalArgumentException("The origin and destination can not be same places!");
        }
            
        this.flight_Number = flight_num;
        this.price = price;
        this. origin = origin;
        this.destination = destination;
        this.airplane_type = airplane;
    }
    
    
    
    public void setFlightNumber(int flight_Num){
        this.flight_Number = flight_Num;
    }
    
    public void setAirline(String airline){
        this.airline = airline; 
    }
    
    public String getAirline(){
        return this.airline;
    }    
    
    public int getFlightNumber(){
        return this.flight_Number;
    }
    
    public void setPrice(float price){
        this.price = price;
    }
    
    public float getPrice(){
        return this.price;
    }
    
    public void setOrigin(String origin){
        this.origin = origin;
    }
    
    public String getOrigin(){
        return this.origin;
    }
    
    public void setDestination(String destination){
        this.destination = destination;
    }
    
    public String getDestination(){
        return this.destination;
    }
    
    public void setairplane(String airplane){
        this.airplane_type = airplane;
    }
    
    public String getairplane(){
        return this.airplane_type;
    }
    
    @Override
    public String toString(){
        return "Flight Number: " + flight_Number + ", " + origin + " to " + destination + ", " + "Price: " + price + "$" + ", Airplane: " + airplane_type;
    }

}
