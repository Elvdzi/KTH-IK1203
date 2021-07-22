import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class ConcHTTPAsk {
    public static void main(String[] args) throws IOException {
        int portnumb = Integer.parseInt(args[0]);

        // creates a server that creates threads, one for each request
        try {
            ServerSocket welcomeSocket = new ServerSocket(portnumb);

            while (true) {
                Socket clientsocket = welcomeSocket.accept();
                Runnable r = new MyRunnable(clientsocket);
                new Thread(r).start();
            }

        } catch (Exception except) {
            System.out.println("Server error");
        }
    }

}
