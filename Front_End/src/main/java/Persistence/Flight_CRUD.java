/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

/**
 *
 * @author student
 */
import Helper.Flight;
//import Business.Airline_Login;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class Flight_CRUD {
    
    private static Connection getConnect() {
        Connection con = null;
        try {
            // If you're using Java 6 or newer, you don't need to load the driver explicitly
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connection = System.getenv("DB_URL");
            String jdbcUrl = "jdbc:mysql://"+ connection +"/amirbaba?serverTimezone=UTC";
            con = DriverManager.getConnection(jdbcUrl, "root", "student");
            System.out.println("Connected");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Flight_CRUD.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException("Driver class not found", e);
        } catch (SQLException e) {
            Logger.getLogger(Flight_CRUD.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException("Database connection failed", e);
        }
        return con;
    }
    
    public ArrayList<Flight> read(String airline) throws ClassNotFoundException{
        ArrayList<Flight> flight = new ArrayList<Flight>();
        Connection con = getConnect();
       
        try{
            PreparedStatement ps = con.prepareStatement("SELECT f.flightNumber, airPlanetype, origin, destination, price FROM Flight f, Flight_Airline fa WHERE f.flightNumber = fa.flightNumber AND fa.username = ?");
            ps.setString(1, airline);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                int flight_Num = rs.getInt("flightNumber");
                String airplane = rs.getString("airPlanetype");
                String origin = rs.getString("origin");
                String destination = rs.getString("destination");
                float price = rs.getFloat("price");
                
                
                flight.add(new Flight(flight_Num, price, origin, destination, airplane));
            }
            con.close();
            
        }catch (SQLException e){
            e.printStackTrace();
        };
        
        return flight;
    }
    
    public ArrayList<Flight> readall() throws ClassNotFoundException{
        ArrayList<Flight> flight = new ArrayList<Flight>();
        Connection con = getConnect();
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Flight");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                int flight_Num = rs.getInt("flightNumber");
                String airplane = rs.getString("airPlanetype");
                String origin = rs.getString("origin");
                String destination = rs.getString("destination");
                float price = rs.getFloat("price");
                
                
                flight.add(new Flight(flight_Num, price, origin, destination, airplane));
            }
            con.close();
            
        }catch (SQLException e){
            e.printStackTrace();
        };
        
        return flight;
    }
        
    
    public boolean create(Flight flight, String username) throws ClassNotFoundException{
       boolean sucsess = false;
       int flightNum = flight.getFlightNumber();
       String airPlanetype = flight.getairplane();
       String origin = flight.getOrigin();
       String destination = flight.getDestination();
       float price = flight.getPrice();
       
       Connection con = getConnect();
       
       try{
            PreparedStatement ps = con.prepareStatement("insert into Flight values (?, ?, ?, ?, ?)");
            ps.setInt(1,flightNum);
            ps.setString(2, airPlanetype);
            ps.setString(3, origin);
            ps.setString(4, destination);
            ps.setFloat(5, price);
            
            PreparedStatement ps2 = con.prepareStatement("insert into Flight_Airline values (?, ?)");
            ps2.setInt(1, flightNum);
            ps2.setString(2, username);
            
            int result = ps.executeUpdate();
            int result2 = ps2.executeUpdate();
            
            sucsess = result == 1 && result2 == 1;
            con.close();
            
        }catch (SQLException e){
            e.printStackTrace();
        };
        
        return sucsess;
    }
}

