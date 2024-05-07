package Helper;

/**
 *
 * @author student
 */
public class Airline {
    private String username, password, email, name;
    private int phoneNumber;
    
    public Airline(String username, String password){
        this.username = username;
        this.password = password;        
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public void setPhoneNumber(int phone){
        this.phoneNumber = phone;
    }
    
    public int getPhoneNumber(){
        return this.phoneNumber;
    }
}
