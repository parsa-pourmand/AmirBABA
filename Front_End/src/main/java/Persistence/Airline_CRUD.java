package Persistence;
import javax.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import Helper.Airline;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author student
 */
public class Airline_CRUD {
    
    private static Connection getConnect(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connection = System.getenv("DB_URL");
            con=DriverManager.getConnection("jdbc:mysql://"+ connection +"/amirbaba?serverTimezone=UTC", "root", "student");
            System.out.println("Connected");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        };
        return con;
    }
    
    
    public static boolean checkLogin(Airline airline){
        boolean result = false;
        Connection con = getConnect();
        
        String username = airline.getUsername();
        String password = airline.getPassword();
        try{
        PreparedStatement ps = con.prepareStatement("select username from Airline where username=? and password=?");
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
    
    
    public static Airline read(String username){
        Airline airline = null;
        Connection con = getConnect();
        try{
            PreparedStatement ps = con.prepareStatement("select * from Airline where username=?");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String password = rs.getString("password");
                String email = rs.getString("Email");
                int phone = rs.getInt("phoneNumber");
                String name = rs.getString("name");
                airline = new Airline(username, password);
                airline.setEmail(email);
                airline.setPhoneNumber(phone);
                airline.setName(name);
            }else{
                airline = null;
            }
            con.close();
            
        }catch (SQLException e){
            e.printStackTrace();
        };
        
        return airline;
    }
    
    public boolean creat(Airline airline){
        boolean sucsess = false;
       String username = airline.getUsername();
       String password = airline.getPassword();
       String email = airline.getEmail();
       int phone = airline.getPhoneNumber();
       String name = airline.getName();
       Connection con = getConnect();
       
       try{
            PreparedStatement ps = con.prepareStatement("insert into Airline values (?, ?, ?, ?, ?)");
            ps.setString(1,username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setInt(4, phone);
            ps.setString(5, name);
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
