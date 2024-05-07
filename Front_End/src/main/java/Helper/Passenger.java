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
public class Passenger {
    private String firstname, lastname, email, password, username;
    
    
    public Passenger(String username, String password){
        this.username = username;
        this. password = password;
    }
        
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public void setFirstname(String fname){
        this.firstname = fname;
    }
    
    public String getFirstname(){
        return this.firstname;
    }
    
    public void setLastname(String lname){
        this.lastname = lname;
    }
    
    public String getLastname(){
        return this.lastname;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getPassword(){
        return this.password;
    }
    
}
