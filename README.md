# Chatroom-Application
Chat server application with SQL database integration for account storage(personal project) <br>
Rahul Kayithi <br>
(Please refer to the PDF for helpful visuals!) <br>
I built this chat server application to practice SQL database integration into a Java program, as well as a client-server interaction using sockets.  It allows a new user to set up an account using a username, password, and email address, and these 3 fields are then recorded in a  table in an SQL database. I used a Microsoft SQL server for this particular application, but I was also able to successfully test with a MySQL database as well. <br>

In the UserRegistration class, after the user inputs the necessary fields, a confirmation email is sent to the email address they provided, confirming the creation of their new Chatroom account. <br>

The ChatServer class allows them to log into the chatroom using their username and password, which will be checked against the saved information in the database table. They will only be able to log into the chatroom if the username and password match up. <br>

The ChatClient class creates a socket that connects to the ServerSocket in the ChatServer application.  After this is executed, the user can then communicate with the server/admin using the chat. <br>

## **How to set up the application** <br>

The UserRegistration and ChatServer class both require a connection URL to connect to the database server. This is the example URL that must be tweaked based on your system:

	private static final String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:<port num>;databaseName=<dbname>;user=<username>;password=<password>; encrypt = true;trustServerCertificate=true";

This example is for the Microsoft SQL server.  Further information for creating a connection URL and establishing a connection can be found here: <br>
https://learn.microsoft.com/en-us/sql/connect/jdbc/building-the-connection-url?view=sql-server-ver16 <br>
https://learn.microsoft.com/en-us/sql/connect/jdbc/working-with-a-connection?view=sql-server-ver16v <br>

For this project, I used Microsoft SQL Server Management Studio to set up the server database and table. 
The relevant database “chatdb” can be created from an SQL query by executing this command:

CREATE DATABASE chatdb;

Then, the relevant table “users” can be created by executing this command:

USE chatdb; <br>

CREATE TABLE users ( <br>
    id INT PRIMARY KEY IDENTITY, <br>
    username VARCHAR(255) NOT NULL, <br>
    password VARCHAR(255) NOT NULL, <br>
	email VARCHAR(320) NOT NULL <br>
); <br>

At this point, the database is fully built for the project. You must also download a JDBC driver and add the JAR file to the Java project class path. The JAR file for the Microsoft JDBC driver can be downloaded from this link: <br>
https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver16 <br>

In addition to the JDBC driver, 2 more JAR files must be imported to the class path libraries; javax.mail.jar and activation.jar 
These JAR files are necessary for the email functionality of the UserRegistration class which sends a confirmation email to the user after their account creation. Those can be found at these links: <br>
https://javaee.github.io/javamail/ <br>
https://www.oracle.com/java/technologies/java-beans-activation.html <br>

Next, we must input the email information for the account which will send out the confirmation email.  <br>

The hostname is unique to each email provider. The hostname and the port number can easily be found by googling. This link is a reference for the Gmail SMTP: <br>
https://kinsta.com/blog/gmail-smtp-server/ <br>

With that, the Chat application should be fully functional on your end. Open your SQL server and create the database and table. Then run the UserRegistration class to create an account which will be saved to the database. After, run the  ChatServer class to start the server, and finally, run the ChatClient class to complete the connection. After logging into a saved account, you will be able to type in the chatroom.


Thanks for checking out my project!
