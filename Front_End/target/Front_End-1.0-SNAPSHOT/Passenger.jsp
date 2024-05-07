<%-- 
    Document   : Passenger
    Created on : Feb 22, 2024, 9:57:47 PM
    Author     : student
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AmirBABA/Passenger</title>
        <style>
            body{
                background-color: #C0C0C0;
            }
            
            table {
                margin-top: 110px; /* Adjust this value as needed to move the table down */
                width: 50%; /* Make the table width responsive */
                height: 70%;
                margin-left: auto; /* Center the table horizontally */
                margin-right: auto;
                background-color: rgba(240, 240, 240, 0.8); /* Semi-transparent white background */
                border-collapse: collapse; /* Collapse borders */
                border-spacing: 100px;
            }
            
            .btn-group .button{
                background-color: rgba(240, 240, 240, 0.8);
                border: solid;
                color: black;
                padding: 15px 0; /* Adjust padding to your liking */
                text-align: center;
                text-decoration: none;
                display: block; /* Make the buttons block elements to fill the container */
                font-size: 18px;
                cursor: pointer;
                border-collapse: collapse;
                width: 20%; /* Set button width relative to its parent */
                box-sizing: border-box; /* Include padding and border in the element's total width and height */
                margin-bottom: 5px; /* Add space between the buttons */
                font-family: Georgia, serif;
            }
       
        </style>
    </head>
    <body>
        <%@ page import = "java.util.ArrayList"%>
        <%@ page import = "Helper.TicketXML"%>
        <%@ page import = "Helper.Ticket"%>
        <%@ page import = "Helper.Flight"%>
        <%@ page import = "Helper.FlightXML"%>
        <center><h2>Welcome <%= request.getAttribute("username")%> </h2></center>
        
        <h3>Available Flights:</h3>
        
        <%
            ArrayList<Flight> myflight = (ArrayList<Flight>) request.getAttribute("Flights");
            
            
        %>
        
        
        <%
            ArrayList<String> flight_descrip = new ArrayList<String>();
 
            for(Flight flight : myflight){
                String description = flight.toString();
                flight_descrip.add(description);
            }
            
        %>
        
        <form action="FrontEnd" method="post"> 
            <input type="hidden" name="pageName" value="buyTicket"/>
            <select name="Buy">
                <% 
                    for(int i =0; i<flight_descrip.size(); i++){
                %>
                <option value ="<%=myflight.get(i).getFlightNumber()%>"> <%=flight_descrip.get(i)%> </option>
                
                <% } %>
            </select> 
            <p><input type="submit" value="Buy"></p>
        </form>
        
        <h3> Your Tickets:</h3>
        
        <form method = "post">  
        
            <table border="2" align="center" cellpadding="5" cellspacing ="5">
                <tr>
                    <td>Ticket Number</td>
                    <td> </td>
                    <td>First Name</td>
                    <td> </td>
                    <td>Last Name</td>
                    <td> </td>
                    <td>Email</td>
                    <td> </td>
                    <td>Flight Number</td>
                </tr>
                
                <%
                    ArrayList<Ticket> mytickets = (ArrayList<Ticket>) request.getAttribute("Tickets");
                    if(mytickets!= null){
                    for(Ticket ticket : mytickets){
                        
                %>
                            <tr>
                                <td><%=ticket.getTicketNumber()%></td>
                                <td>  </td>
                                <td><%=ticket.getFirstname() %></td>
                                <td>  </td>
                                <td><%=ticket.getLastname() %></td>
                                <td>  </td>
                                <td><%=ticket.getEmail() %></td>
                                <td>  </td>
                                <td><%=ticket.getFlightNum() %></td>
                            </tr>
                <%        
                        }
                    }
                %>
            </table>
        </form> 
            <div class="btn-group">
                <a href="Passenger_Login.html" class="button">Logout</a>
            </div>
    </body>
</html>
