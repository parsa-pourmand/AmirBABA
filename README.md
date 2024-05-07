**Overview:**

AmirBABA is a cutting-edge flight booking platform designed to streamline the interaction between passengers and airlines. 
Developed using a micro-services architecture, this application not only facilitates robust scalability and service isolation 
but also ensures tailored functionalities to cater to the distinct needs of its user groups.

**Platform Highlights:**

**User-Friendly Registration System:** Features dedicated microservices for user registration, supporting both passengers and airlines. 
New users can easily sign up with a username and password, integrating seamlessly into the platform.
 
**Dynamic Flight Management:** The Flight microservice allows airlines to add and manage their flights, providing a comprehensive overview of schedules. 
Passengers logged into the system gain access to a real-time list of all available flights across various airlines, enabling them to make informed booking decisions.
 
**Efficient Ticketing Interface:** Through the Ticket microservice, passengers can book tickets and view their purchase history, while airlines can monitor ticket sales for their flights. 
This service ensures that all transactions are handled smoothly, with immediate updates and confirmations.
 
**Seamless Front-End Experience:** The FrontEnd microservice handles all user interactions through a carefully designed interface, ensuring a smooth and responsive experience 
across HTML and JSP files, supported by robust FrontEnd Servlets.

**Technical Details:**

**Dedicated Databases:** Each microservice, along with its own MySQL database, is containerized using Docker. This approach not only simplifies deployment across 
different computing environments but also ensures consistency and scalability of the application infrastructure.
 
**Asynchronous and Synchronous Communications:** Utilizes Kubemq for asynchronous messaging between microservices, ensuring fast and reliable data transfer without 
waiting for responses. For synchronous operations, a Kubernetes cluster facilitates direct, real-time communication among services, crucial for operations requiring 
immediate data consistency.
 
**N-layer Architecture:** Each microservice is structured using an n-layer architecture, segregating business logic, data access, and user interface. 
This separation enhances maintainability and allows for agile development and testing practices.
 
**Cloud Deployment and Kubemq Integration:** Hosted on Google Cloud, AmirBABA leverages the robustness of cloud infrastructure and employs Kubemq for 
reliable inter-service messaging, guaranteeing that the system performs optimally even under high demand.


**Conclusion:**

AmirBABA merges the convenience of online booking with the efficiency of modern software architectures, making it an ideal choice for today’s fast-paced travel industry. 
It provides a transparent, easy-to-navigate platform for both passengers planning their journeys and airlines managing their operations.

**Tools Used:**

1- Java

2- Apatche Tomcat

3- Ducker Container

4- Kubernetes Cluster

5- Kubmq Channel 

6- Google Cloud Environment

7- MySQL Database

8- HTML

9- CSS

10- Servlet
