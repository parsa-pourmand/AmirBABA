<%-- 
    Document   : Airline.jsp
    Created on : Feb 2, 2024, 7:30:14 PM
    Author     : student
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AmirBaBa/Airline</title>
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
    <center><h2>Welcome <%= request.getAttribute("username")%> </h2></center>
        <h4>Add Flight:</h4>
        <form action="FrontEnd" method="post">
        <input type="hidden" name="pageName" value="addFlight"/>

            <table>
                <tr>
                    <td>Flight Number</td>
                    <td>Airplane Type</td>
                    <td>Origin</td>
                    <td>Destination</td>
                    <td>Price</td>
                </tr>
                <tr>
                    <td><input type="text" name="Flight Number"></td>
                    <td><input type ="text" name="airplane"></td>
                    <td><input type="text" name="Origin"></td>
                    <td><input type="text" name="Destination"></td>
                    <td><input type="text" name="Price"></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Add"></td>
                </tr>
            </table> 
        </form>
        
        <hr>
        
        <h4>Your Flights:</h4>
        
        <form method = "post">  
        <%@ page import = "java.util.ArrayList"%>
        <%@ page import = "Helper.Flight"%>
        <%@ page import = "Helper.FlightXML"%>
            <table border="2" align="center" cellpadding="5" cellspacing ="5">
                <tr>
                    <td>Flight Number</td>
                    <td> </td>
                    <td>Origin</td>
                    <td> </td>
                    <td>Destination</td>
                    <td> </td>
                    <td>Price</td>
                </tr>
                <%
                    ArrayList<Flight> myflights = (ArrayList<Flight>) request.getAttribute("Flights");
                    if(myflights!=null){
                        for(Flight flight : myflights){
                        
                %>
                            <tr>
                                <td><%=flight.getFlightNumber() %></td>
                                <td>  </td>
                                <td><%=flight.getOrigin() %></td>
                                <td>  </td>
                                <td><%=flight.getDestination() %></td>
                                <td> </td>
                                <td><%=flight.getPrice() %></td>
                            </tr>
                <%        
                        }
                    }
                %>
            </table>
        </form>      
        <div class="btn-group"> 
            <a href="Airline_Login.html" class="button">Logout</a>
        </div>
    </body>
</html>