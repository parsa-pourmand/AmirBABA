/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import javax.xml.bind.annotation.XmlElement;



/**
 *
 * @author student
 */

public class Ticket {
   private String email, firstName, lastName, username;
   private int flight_Num;
   
   @XmlElement(name = "ticketNumber")
   private int TicketNumber;
   
   public Ticket(){}
   
   public Ticket(int ticket){
       this.TicketNumber = ticket;   
   }
   
   public void setEmail(String email){
       this.email = email;
   }
   
   
   public String getEmail(){
       return this.email;
   }
   
   public void setUsername(String username){
       this.username = username;
   }
   
   public String getUsername(){
       return this.username;
   }
   
   public void setFirstname(String firstName){
       this.firstName = firstName;
   }
   
   public String getFirstname(){
       return this.firstName;
   }
   
   public void setLastname(String lastname){
       this.lastName = lastname;
   }
   
   public String getLastname(){
       return this.lastName;
   }
   
   public void setFlightNum(int flightNum){
       this.flight_Num = flightNum;
   }
   
   public int getFlightNum(){
       return this.flight_Num;
   }
   
   public int getTicketNumber(){
       return this.TicketNumber;
   }
}
