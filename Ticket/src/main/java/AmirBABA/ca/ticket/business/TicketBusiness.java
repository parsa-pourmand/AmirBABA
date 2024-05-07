/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AmirBABA.ca.ticket.business;

import AmirBABA.ca.ticket.helper.Ticket;
import AmirBABA.ca.ticket.persistence.Ticket_CRUD;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author amir
 */
public class TicketBusiness {
    
    public ArrayList<Ticket> getTickets(String username){
        Ticket_CRUD ticketCRUD = new Ticket_CRUD();
        ArrayList<Ticket> tickets = new ArrayList<>();
        
        try {
            tickets = ticketCRUD.read(username);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(TicketBusiness.class.getName()).log(Level.SEVERE, null, e);
        }
        return tickets;
       
    }
    
    public boolean addTicket(Ticket ticket){
    Ticket_CRUD ticketCRUD = new Ticket_CRUD();
        try {
            boolean status = ticketCRUD.create(ticket);
            
            if(status){
                Messaging.sendmessage("Ticket:" + ticket.getFirstname() + ":" + ticket.getLastname() + ":" + ticket.getEmail() + ":" + ticket.getTicketNumber() + ":" + ticket.getFlightNum());
            }
            return status; 
            
        } catch (ClassNotFoundException e) {
            Logger.getLogger(TicketBusiness.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
