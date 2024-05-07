/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package front_end;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.NewCookie;
import Business.Business;
import Helper.Flight;
import Helper.FlightXML;
import Helper.Passenger;
import Helper.Airline;
import Helper.Ticket;
import Helper.TicketXML;
import Persistence.Passenger_CRUD;
import java.util.Random;
import java.util.ArrayList;

/**
 *
 * @author student
 */
@WebServlet(name = "FrontEnd", urlPatterns = {"/FrontEnd"})
public class FrontEnd extends HttpServlet {

    Authenticate autho;

    public FrontEnd() {
        autho = new Authenticate();
    }
    private final String AirlineauthenticationCookieName = "Airline_login_token";
    private final String PassengerauthenticationCookieName = "Passenger_login_token";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private Map.Entry<String, String> isAuthenticated(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        
        System.out.println("TOKEN IS");
        try {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName());
                if (cookie.getName().equals(AirlineauthenticationCookieName) || cookie.getName().equals(PassengerauthenticationCookieName)) {
                    token = cookie.getValue();
                }
            }
        } catch (Exception e) {

        }
        if (!token.isEmpty())
           try {
            if (this.autho.verify(token).getKey()) {
                  Map.Entry entry= new  AbstractMap.SimpleEntry<String, String>
                             (token,this.autho.verify(token).getValue());
            return entry;

            } else {
                 Map.Entry entry= new  AbstractMap.SimpleEntry<String, String>("","");
            return entry;
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
        }

       Map.Entry entry= new  AbstractMap.SimpleEntry<String, String>("","");
            return entry;

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = isAuthenticated(request).getKey();
        String uname = isAuthenticated(request).getValue();
        String hiddenParam = request.getParameter("pageName");
        switch (hiddenParam) {
            case "passenger":
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                boolean isAuthenticated = Business.Passenger_Authentication(username, password);
                if (isAuthenticated) {
                    request.setAttribute("username", username);
                    token = autho.createJWT("FrontEnd", username, 100000);

                    Cookie newCookie = new Cookie(PassengerauthenticationCookieName, token);
                    response.addCookie(newCookie);
                    String Ticket_query = username;
                    
                    ArrayList<Flight> flight = retreiveFlightsFromBackend(null, token);
                    request.setAttribute("Flights", flight);
                    
                    ArrayList<Ticket> result = retreiveTicketsFromBackend(Ticket_query, token);
                    
                    request.setAttribute("Tickets", result);

                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("Passenger.jsp");

                    requestDispatcher.forward(request, response);
                    break;
                }else{
                   RequestDispatcher requestDispatcher = request.getRequestDispatcher("Passenger_Login.html");
                   requestDispatcher.forward(request, response);
                   break;
                }
                
            case "airline":

                ArrayList<Flight> result;
                String userName = request.getParameter("username");
                String Passwrod = request.getParameter("password");
                boolean exist = Business.Airline_Authentication(userName, Passwrod);
                if(exist){
                    
                    request.setAttribute("username", userName);
                    token = autho.createJWT("FrontEnd", userName, 100000);

                    Cookie newCookie = new Cookie(AirlineauthenticationCookieName, token);
                    response.addCookie(newCookie);
                    
                    String Flight_query = userName;
                    result = retreiveFlightsFromBackend(Flight_query, token);
                    request.setAttribute("Flights", result);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("Airline.jsp");

                    requestDispatcher.forward(request, response);
                    break; 
                }else {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("Airline_Login.html");

                    requestDispatcher.forward(request, response);
                    break;
                }
                
            case "addFlight":
                
                int flightNumber = Integer.parseInt(request.getParameter("Flight Number"));
                String airplane = request.getParameter("airplane");
                String origin = request.getParameter("Origin");
                String destination = request.getParameter("Destination");
                float price = Float.parseFloat(request.getParameter("Price"));
            
            if(origin.equals("") || destination.equals("") || airplane.equals("")){
                request.getSession().setAttribute("error_add", "Please Fill all fields with appropriate information");
                RequestDispatcher rd = request.getRequestDispatcher("Airline.jsp");
                rd.forward(request, response);
            }
            
            Flight newflight = new Flight(flightNumber, price, origin, destination, airplane);
            
            
            boolean sent = sendFlightsToBackend(newflight, token);
             if(sent){
                RequestDispatcher rd = request.getRequestDispatcher("AddFlightSuccess.html");
                rd.forward(request, response);
                break;
             }else{
                String Flight_query = uname;
                result = retreiveFlightsFromBackend(uname, token);
                request.setAttribute("Flights", result);
                
                RequestDispatcher rd = request.getRequestDispatcher("Airline.jsp");
                rd.forward(request, response);
                break;
             }
             
            case "buyTicket":
                
                Random rand = new Random();
                int max=10000,min=1000;
                int ticketNum = rand.nextInt(max - min + 1) + min;
        
                Ticket ticket = new Ticket(ticketNum);
        
                int flightNum = Integer.parseInt(request.getParameter("Buy"));
                ticket.setFlightNum(flightNum);
                String passenger_name = uname;
                Passenger_CRUD passenger_crud = new Passenger_CRUD();
                Passenger passenger = passenger_crud.read(passenger_name);
                String firstName = passenger.getFirstname();
                String lastName = passenger.getLastname();
                String email = passenger.getEmail();
        
                ticket.setEmail(email);
                ticket.setFirstname(firstName);
                ticket.setLastname(lastName);
                ticket.setUsername(passenger_name);
                boolean success = sendTicketsToBackend(ticket, uname);
                
                if(success){
                RequestDispatcher rd = request.getRequestDispatcher("BuySuccess.html");
                rd.forward(request, response);
                break;
             }else{
                RequestDispatcher rd = request.getRequestDispatcher("Passenger.jsp");
                rd.forward(request, response);
                break;
             }
                
            case "passengerBack":
                 request.setAttribute("username", uname);
                String Ticket_query = uname;

                ArrayList<Flight> flight = retreiveFlightsFromBackend(null, token);
                request.setAttribute("Flights", flight);
                    
                ArrayList<Ticket> result1 = retreiveTicketsFromBackend(Ticket_query, token);
                request.setAttribute("Tickets", result1);
                
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("Passenger.jsp");

                requestDispatcher.forward(request, response);
                break;
                
            case "airlineBack":
                request.setAttribute("username", uname);
                String Flight_query = uname;
                ArrayList<Flight> results = retreiveFlightsFromBackend(Flight_query, token);
                request.setAttribute("Flights", results);
                RequestDispatcher requestDispatcher2 = request.getRequestDispatcher("Airline.jsp");

                requestDispatcher2.forward(request, response);
                break; 
               
            case "airline_reg":
                String Username = request.getParameter("username");
                String name = request.getParameter("name");
                int phone = Integer.valueOf(request.getParameter("phone"));
                String Email = request.getParameter("email");
                String pass = request.getParameter("password");
                
                Airline airline = new Airline(Username, pass);
                airline.setName(name);
                airline.setEmail(Email);
                airline.setPhoneNumber(phone);
                
                boolean register = RegisterAirlineToBackend(airline);
                System.out.println(register);
                if(register){
                    RequestDispatcher rd = request.getRequestDispatcher("Airline_Login.html");
                    rd.forward(request, response);
                    break;
                }else{
                    RequestDispatcher rd = request.getRequestDispatcher("Airline_Register.jsp");
                    rd.forward(request, response);
                    break;
                }
                
            case "passenger_reg":
                String UserName = request.getParameter("username");
                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String E_mail = request.getParameter("email");
                String Pass = request.getParameter("password");
                
                Passenger passe = new Passenger(UserName, Pass);
                passe.setFirstname(firstname);
                passe.setEmail(E_mail);
                passe.setLastname(lastname);
                
                boolean pass_register = RegisterPassengerToBackend(passe);

                if(pass_register){
                    RequestDispatcher rd = request.getRequestDispatcher("Passenger_Login.html");
                    rd.forward(request, response);
                    break;
                }else{
                    RequestDispatcher rd = request.getRequestDispatcher("Passenger_Register.jsp");
                    rd.forward(request, response);
                    break;
                }      
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private ArrayList<Flight> retreiveFlightsFromBackend(String Flight_query, String token) {
        try {
            return (Business.RetreiveFlights(Flight_query, token));
        } catch (IOException ex) {
            Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
            return (null);
        }

    }
    
    private ArrayList<Ticket> retreiveTicketsFromBackend(String Ticket_query, String token) {
        try {
            return (Business.RetreiveTickets(Ticket_query, token));
        } catch (IOException ex) {
            Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
            return (null);
        }

    }
    
    private boolean sendFlightsToBackend(Flight newFlight, String token){
        
        
        return (Business.SendFlight(newFlight, token));        
    }
    
    private boolean sendTicketsToBackend(Ticket newTicket, String token){
        
        
        return (Business.SendTicket(newTicket, token));        
    }
    
    private boolean RegisterAirlineToBackend(Airline airline){
        
        return (Business.AddAirline(airline));
    }
    
    private boolean RegisterPassengerToBackend(Passenger passenger){
        
        return (Business.AddPassenger(passenger));
    }

}
