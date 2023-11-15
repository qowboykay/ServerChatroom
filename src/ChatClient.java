import java.io.*;
import java.net.*;

/*
 * Class for enabling client side operations
 */
public class ChatClient {
    public static void main(String[] args) throws Exception {
        // Create a socket and connect to the server
        Socket socket = new Socket("localhost", 3000);

        // Create input and output streams
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Create a new thread to listen for messages from the server
        Thread thread = new Thread(new ServerListener(in));
        thread.start();

        // Send login information to the server
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your username and password:");
        String loginInfo = userIn.readLine();
        out.println(loginInfo);

        // Wait for a welcome message from the server
        String welcome = in.readLine();
        if (welcome.startsWith("Welcome")) {
            System.out.println(welcome);
            System.out.println("You can start sending messages now.");
        } else {
            System.out.println(welcome);
            socket.close();
            return;
        }

        // Send messages to the server
        while (true) {
            String message = userIn.readLine();
            out.println(message);
        }
    }
}

class ServerListener implements Runnable {
    private BufferedReader in;

    public ServerListener(BufferedReader in) {
        this.in = in;
    }

    public void run() {
        while (true) {
            try {
                String message = in.readLine();
                if (message != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
