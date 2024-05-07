/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AmirBABA.ca.ticket.persistence;

import AmirBABA.ca.ticket.helper.Ticket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author amir
 */

public class Ticket_CRUD {
    
    private static Connection getConnect() {
        Connection con = null;
        try {
            // If you're using Java 6 or newer, you don't need to load the driver explicitly
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connection = System.getenv("DB_URL");

            String jdbcUrl = "jdbc:mysql://" + connection + "/Ticket?serverTimezone=UTC";
            con = DriverManager.getConnection(jdbcUrl, "root", "student");
            System.out.println("Connected");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Ticket_CRUD.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException("Driver class not found", e);
        } catch (SQLException e) {
            Logger.getLogger(Ticket_CRUD.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException("Database connection failed", e);
        }
        return con;
    }
    
    public ArrayList<Ticket> read(String username) throws ClassNotFoundException{
        ArrayList<Ticket> ticket = new ArrayList<>();
        Connection con = getConnect();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT TicketNumber, flightNumber, FirstName, LastName, Email, username FROM Ticket WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                int ticket_Num = rs.getInt("TicketNumber");
                int flightNum = rs.getInt("flightNumber");
                String Firstname = rs.getString("FirstName");
                String Lastname = rs.getString("LastName");
                String email = rs.getString("Email");
                Ticket tick = new Ticket(ticket_Num);
                
                tick.setEmail(email);
                tick.setFirstname(Firstname);
                tick.setLastname(Lastname);
                tick.setFlightNum(flightNum);
                tick.setUsername(username);
                ticket.add(tick);
                
                
            }
            con.close();
            
        }catch (SQLException e){
            e.printStackTrace();
        };
        
        return ticket;
    }
    
    @SuppressWarnings("empty-statement")
    public boolean create(Ticket ticket) throws ClassNotFoundException{
        boolean sucsess = false;
       int flightNum = ticket.getFlightNum();
       String FirstName = ticket.getFirstname();
       String Lastname = ticket.getLastname();
       String email = ticket.getEmail();
       String username = ticket.getUsername();
       int ticketNum = ticket.getTicketNumber();
       
       Connection con = getConnect();
       
       try{
            PreparedStatement ps = con.prepareStatement("insert into Ticket values (?, ?, ?, ?, ?, ?)");
            ps.setInt(1,ticketNum);
            ps.setString(2, FirstName);
            ps.setString(3, Lastname);
            ps.setString(4, email);
            ps.setInt(5, flightNum);
            ps.setString(6, username);
            int result = ps.executeUpdate();
            
            
            sucsess = result == 1;
            con.close();
            
        }catch (SQLException e){
        };
        
        return sucsess;
    }
    
}

