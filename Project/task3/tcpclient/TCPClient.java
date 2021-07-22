package tcpclient;

import java.net.*;
import java.io.*;
import java.lang.*;
import java.nio.charset.StandardCharsets;

public class TCPClient {

    public static String askServer(String hostname, int port, String ToServer) throws IOException {
        String modifieradMening = "";

        Socket klientSocket = new Socket(hostname, port);

        klientSocket.setSoTimeout(3000);

        OutputStream outputServer = klientSocket.getOutputStream();

        InputStream inputServer = klientSocket.getInputStream();

        if (ToServer != null) {
            ToServer += '\n';
            outputServer.write(ToServer.getBytes());
        }

        try {
            int a = inputServer.read();
            while (a != -1) {
                modifieradMening += (char) a;
                a = inputServer.read(); // läsa in nästa rad
            }

        } catch (SocketTimeoutException e) {

        }

        // System.out.println("FRÅN SERVER:" + modifieradMening);
        klientSocket.close();
        return modifieradMening;
    }

    public static String askServer(String hostname, int port) throws IOException {
        String modifieradMening = "";

        Socket klientSocket = new Socket(hostname, port);
        // System.out.println(klientSocket.isConnected());

        klientSocket.setSoTimeout(3000);

        OutputStream outputServer = klientSocket.getOutputStream();

        InputStream inputServer = klientSocket.getInputStream();

        try {
            int a = inputServer.read();
            while (a != -1) {
                modifieradMening += (char) a;
                a = inputServer.read(); // läsa in nästa rad
            }

        } catch (SocketTimeoutException e) {

        }

        klientSocket.close();
        return modifieradMening;

    }
}
