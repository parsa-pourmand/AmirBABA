<%-- 
    Document   : Airline_Register
    Created on : Feb 24, 2024, 4:43:51 PM
    Author     : student
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>AmirBABA/Register</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <style>
            body{
                background-color: #C0C0C0;
            }
            
            h3 {
                text-align: center; /* Optional: Center the 'Hello World' text */
                color: black; /* Optional: Change text color for visibility */
                padding-top: 20px; /* Optional: Add padding to position the text */
                font-family: Georgia, serif;
                font-size: 50px;
            }
            
            input[type="text"] {
                width: 95%; /* Make input field responsive within the table cell */
                padding: 5px; /* Add padding inside the input field */
                font-size: 14px; /* Slightly smaller font size than cell text */
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
                margin-left: auto; /* Center the table horizontally */
                margin-right: auto;
                font-size: 18px;
                cursor: pointer;
                border-collapse: collapse;
                width: 20%; /* Set button width relative to its parent */
                margin-top: 10px;
                box-sizing: border-box; /* Include padding and border in the element's total width and height */
                margin-bottom: 5px; /* Add space between the buttons */
                font-family: Georgia, serif;
            }
        </style>
    </head>
    <body>
        <h3>Welcome to AmirBABA</h3>
        <p>Thank you for joining us.</p>
        <p>Please fill the following form to complete your registration process.</p>
        <form action="FrontEnd" method="post">
            <input type="hidden" name="pageName" value="airline_reg"/>
            <table>
                
                <tr>
                    <td>Username:</td>
                    <td><input type="text" name="username" size="25"></td>
                </tr>
                
                <tr></tr>
            
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="name" size="25"></td>
                </tr>
                
                <tr></tr>
            
                <tr>
                    <td>Phone Number:</td>
                    <td><input type="text" name="phone" size="25"></td>
                </tr>
                
                <tr></tr>
            
                <tr>
                    <td>Email:</td>
                    <td><input type="text" name="email" size="25"></td>
                </tr>
                
                <tr></tr>
                
                <tr>
                    <td>Password:</td>
                    <td><input type="text" name="password" size="25"></td>
                </tr>
            </table>
            
            <div class="btn-group">     
                <button type="submit" class="button">Register</button>
            </div>
                <%
                    String login_msg = (String)request.getSession().getAttribute("error");
                    if(login_msg!=null){
                        out.println("<font color=red size=4px>" + login_msg + "</font>");
                    }
                %>
        </form>
    </body>
</html>
