import java.io.*;
import java.net.*;
import java.sql.*;

/*
 * Class for hosting the server side operations
 */
public class ChatServer {
	//connection url (this example is for Microsoft SQL server
    private static final String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:<port num>;databaseName=<dbname>;user=<username>;password=<password>; encrypt = true;trustServerCertificate=true";

    
    public static void main(String[] args) throws Exception {
        try (
		ServerSocket ss = new ServerSocket(3000)) {
			System.out.println("Server started at: " + new java.util.Date());

		
			while (true) {
			   
			    Socket socket = ss.accept();
			    System.out.println("Connection from: " + socket.getInetAddress());

			    
			    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			    
			    out.println("Please enter your username and password:");

			    // Read the client's response
			    String[] loginInfo = in.readLine().split(" ");
			    String username = loginInfo[0];
			    String password = loginInfo[1];

			    // Connect to the database
			    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			    Connection conn = DriverManager.getConnection(url);

			    // Prepare and execute a query to check the login information
			    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
			    PreparedStatement stmt = conn.prepareStatement(sql);
			    stmt.setString(1, username);
			    stmt.setString(2, password);
			    ResultSet result = stmt.executeQuery();

			    // Check if the query returned a result
			    if (result.next()) {
			        // Send a welcome message to the client
			        out.println("Welcome to the chat room, " + username + "!");

			        // Create a new thread for the connection
			        ChatThread thread = new ChatThread(socket, username);

			        // Start the thread
			        thread.start();
			    } else {
			        // Send an error message to the client
			        out.println("Invalid username or password. Please try again.");

			        // Close the socket
			        socket.close();
			    }
			}
		}
        
    }
}

class ChatThread extends Thread {
    private Socket socket;
    private String username;

    public ChatThread(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    public void run() {
        try {
            // Creates input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            

            // Listen for messages from the client
            while (true) {
                String message = in.readLine();
                if (message == null) {
                    break;
                }
                System.out.println(username +  ": " + message);
            }
        } catch (IOException e) {
        e.printStackTrace();
        } finally {
        try {
        socket.close();
        } catch (IOException e) {
        e.printStackTrace();
   }
       }
           }
               }

