import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Scanner;


/* 
 * Class for registering new user into db, then confirmation email will be sent.
 */
public class UserRegistration {
	
	//connection url (this example is for Microsoft SQL server
	private static final String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:<port num>;databaseName=<dbname>;user=<username>;password=<password>; encrypt = true;trustServerCertificate=true";

		private static final String EMAIL_FROM = "email@example.com";  //input your email address
		private static final String EMAIL_PASSWORD = "password";      //input your email password
		private static final String EMAIL_HOST = "smtp.email.com";     //input host name
		private static final String EMAIL_PORT = "465";					// input email port number
	    public static void addUser(String username, String password, String email) {
	        try {
        	//connect to db
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url);

            System.out.println("Connected to Microsoft Sql Server");
			
            String sql = "INSERT INTO users (username, password, email)" + "VALUES (?, ?, ?)";
			
            // insert statement
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, email);
			
			int rows = statement.executeUpdate();
			// Send a confirmation email
            sendConfirmationEmail(email, username);
			
			if(rows >0) {
				//account confirmation
				System.out.println("User account has been saved");
			} 
			connection.close();
		} catch (SQLException e) {
			System.out.println("Oops, there's been an error: ");
			e.printStackTrace();
		
	    } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
	}   
	private static void sendConfirmationEmail(String email, String username) {
        Properties props = new Properties();
        props.put("mail.smtp.host", EMAIL_HOST);   
        props.put("mail.smtp.port", EMAIL_PORT);	
        props.put("mail.smtp.socketFactory.port", "465"); 
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, 
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                    	return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Account Confirmation");
            message.setText("Dear " + username + ",\n\nYour account has been successfully created.\n\nBest regards,\n the Chatroom App ");

            Transport.send(message);

            System.out.println("Confirmation email sent to " + email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
	}
        
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            //records client's input into db
            System.out.println("Enter your username:");
            String username = scanner.nextLine();
            System.out.println("Enter your password:");
            String password = scanner.nextLine();
            System.out.println("Enter your email:");
            String email = scanner.nextLine();

            addUser(username, password, email);
        }
    }