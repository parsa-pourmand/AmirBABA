/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AmirBABA.ca.ticket.helper;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author amir
 */

public class Ticket {
   private String email, firstName, lastName, username;
   private int flight_Num;
   
   @XmlElement(name = "ticketNumber")
   private int TicketNumber;
   
   public Ticket(int ticket){
       
       this.TicketNumber = ticket;   
   }
   
   public void setEmail(String email){
       this.email = email;
   }
   
   public String getEmail(){
       return this.email;
   }
   
   public void setFirstname(String firstName){
       this.firstName = firstName;
   }
   
   public void setUsername(String username){
       this.username = username;
   }
   
   public String getUsername(){
       return this.username;
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
