/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;


import javax.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import Helper.Passenger;

/**
 *
 * @author student
 */
public class Passenger_CRUD {
    
    private static Connection getConnect(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connection = System.getenv("DB_URL");
            con=DriverManager.getConnection("jdbc:mysql://"+ connection +"/Passenger?serverTimezone=UTC", "root", "student");
            System.out.println("Connected");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        };
        return con;
    }
    
    public static boolean checkLogin(Passenger passenger){
        boolean result = false;
        Connection con = getConnect();
        String username = passenger.getUsername();
        String password = passenger.getPassword();
        
        try{
            PreparedStatement ps = con.prepareStatement("select username from Passenger where username=? and password=?");
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
               result = true;
            }else{
                result = false;
            }
            con.close();
        } catch (SQLException e){
            e.printStackTrace();
        };
        return result;
    }
    
    public static Passenger read(String username){
        Passenger passenger = null;
        Connection con = getConnect();
        try{
            PreparedStatement ps = con.prepareStatement("select * from Passenger where username=?");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                String firstname = rs.getString("FirstName");
                String lastname = rs.getString("LastName");
                passenger = new Passenger(username, password);
                passenger.setEmail(email);
                passenger.setFirstname(firstname);
                passenger.setLastname(lastname);
            }else{
                passenger = null;
            }
            con.close();
            
        }catch (SQLException e){
            e.printStackTrace();
        };
        
        return passenger;
    }

    public boolean creat(Passenger passenger){
       boolean sucsess = false;
       String username = passenger.getUsername();
       String password = passenger.getPassword();
       String email = passenger.getEmail();
       String firstname = passenger.getFirstname();
       String lastname = passenger.getLastname();
       
       Connection con = getConnect();
       
       try{
            PreparedStatement ps = con.prepareStatement("insert into Passenger values (?, ?, ?, ?, ?)");
            ps.setString(1,username);
            ps.setString(2, email);
            ps.setString(3, firstname);
            ps.setString(4, lastname);
            ps.setString(5, password);
            int result = ps.executeUpdate();
            if(result == 1){
                sucsess = true;
            }else{
                sucsess = false;
            }
            con.close();
            
        }catch (SQLException e){
            e.printStackTrace();
        };
        
        return sucsess;
    }
}
