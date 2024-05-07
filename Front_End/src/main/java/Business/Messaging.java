/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 *
 * @author student
 */

import io.grpc.stub.StreamObserver;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.event.EventReceive;
import io.kubemq.sdk.event.Subscriber;
import io.kubemq.sdk.subscription.EventsStoreType;
import io.kubemq.sdk.subscription.SubscribeRequest;
import io.kubemq.sdk.subscription.SubscribeType;
import io.kubemq.sdk.tools.Converter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import endpoint.MyAppServletContextListener;
import Persistence.Passenger_CRUD;
import Persistence.Airline_CRUD;
import Persistence.Flight_CRUD;
import Persistence.Ticket_CRUD;
import Helper.Passenger;
import Helper.Airline;
import Helper.Flight;
import Helper.Ticket;

public class Messaging {
    public static void Receiving_Events_Store(String cname) throws SSLException, ServerAddressNotSuppliedException {
        String ChannelName = cname, ClientID = "hello-world-subscribe1r";
                String kubeMQAddress = System.getenv("kubeMQAddress");
        Subscriber subscriber = new Subscriber(kubeMQAddress);
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setChannel(ChannelName);
        subscribeRequest.setClientID(ClientID);
        subscribeRequest.setSubscribeType(SubscribeType.EventsStore);
        subscribeRequest.setEventsStoreType(EventsStoreType.StartAtSequence);
        subscribeRequest.setEventsStoreTypeValue(1);

        StreamObserver<EventReceive> streamObserver = new StreamObserver<EventReceive>() {

            @Override
            public void onNext(EventReceive value) {
                try {
                    String val=(String) Converter.FromByteArray(value.getBody());
                    System.out.printf("Event Received: EventID: %s, Channel: %s, Metadata: %s, Body: %s",
                            value.getEventId(), value.getChannel(), value.getMetadata(),
                            Converter.FromByteArray(value.getBody()));
                    String[] msgParts = val.split(":");
                    
                    switch(msgParts[0]){
                        case "Passenger":
                            if(msgParts.length == 6){
                                Passenger_CRUD passenger_crud = new Passenger_CRUD();
                                String FirstName = msgParts[1];
                                String LastName = msgParts[2];
                                String email = msgParts[3];
                                String password = msgParts[4];
                                String username = msgParts[5];

                                Passenger passenger = new Passenger(username, password);
                                passenger.setEmail(email);
                                passenger.setFirstname(FirstName);
                                passenger.setLastname(LastName);
                            
                                passenger_crud.creat(passenger);
                            }
                            break;
                            
                        case "Airline":
                            if(msgParts.length == 6){
                                Airline_CRUD airline_crud = new Airline_CRUD();
                                String name = msgParts[1];
                                String Email = msgParts[2];
                                String Password = msgParts[4];
                                String Username = msgParts[3];
                                String phone = msgParts[5];
                                Airline airline = new Airline(Username, Password);
                                airline.setEmail(Email);
                                airline.setName(name);
                                airline.setPhoneNumber(Integer.parseInt(phone));
                            
                                airline_crud.creat(airline);
                            }
                            break;
                            
                        case "Flight":
                            if(msgParts.length == 7){
                                Flight_CRUD flight_crud = new Flight_CRUD();
                                String flight_num = msgParts[1];
                                String price = msgParts[2];
                                String origin = msgParts[3];
                                String destination = msgParts[4];
                                String airplane = msgParts[5];
                                String usern = msgParts[6];
                                Flight flight = new Flight(Integer.parseInt(flight_num), Float.parseFloat(price), origin, destination, airplane);
                            
                            
                                flight_crud.create(flight, usern);
                            }
                            break;   
                            
                        case "Ticket":
                            
                            if(msgParts.length == 6){
                                Ticket_CRUD ticket_crud = new Ticket_CRUD();
                                String firstName = msgParts[1];
                                String lastName = msgParts[2];
                                String email = msgParts[3];
                                String ticket_num = msgParts[4];
                                String flight_num = msgParts[5];
                                
                                Ticket ticket = new Ticket(Integer.parseInt(ticket_num));
                                ticket.setEmail(email);
                                ticket.setFirstname(firstName);
                                ticket.setLastname(lastName);
                                ticket.setFlightNum(Integer.parseInt(flight_num));
                            
                                ticket_crud.create(ticket);
                            }
                            break;   
                    }
                } catch (ClassNotFoundException e) {
                    System.out.printf("ClassNotFoundException: %s", e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.printf("IOException: %s", e.getMessage());
                    e.printStackTrace();
                } catch (SQLException ex) {
                    Logger.getLogger(MyAppServletContextListener.class.getName()).log(Level.SEVERE, null, ex);
                }  
            }

            @Override
            public void onError(Throwable t) {
                System.out.printf("onError:  %s", t.getMessage());
            }

            @Override
            public void onCompleted() {

            }

        };
        subscriber.SubscribeToEvents(subscribeRequest, streamObserver);

    }
    
}
