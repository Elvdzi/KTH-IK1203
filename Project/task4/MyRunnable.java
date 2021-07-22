import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class MyRunnable implements Runnable {

    Socket clientsocket;

    public MyRunnable(Socket socket) {
        clientsocket = socket;
    }

    public void run() {
        System.out.println("Run!");

        String host;
        String port;
        String input;

        try {

            BufferedReader inputClient = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            DataOutputStream outputClient = new DataOutputStream(clientsocket.getOutputStream());

            host = null;
            port = null;
            input = null;

            String sentence = inputClient.readLine();
            String output = "";

            String[] t = sentence.split("[ &=?/]");

            if (t[2].equals("ask")) {
                for (int i = 0; i < t.length; i++) {
                    if (t[i].equals("hostname")) {
                        host = t[i + 1];
                        i++;
                    }

                    // GET /ask?hostname=time.nist.gov&port=13 HTTP/1.1
                    else if (t[i].equals("port")) {
                        port = t[i + 1];
                        i++;

                    } else if (t[i].equals("string")) {
                        input = t[i + 1];
                        i++;
                    }

                }

                try {
                    output = TCPClient.askServer(host, Integer.parseInt(port), input);
                    String s = ("HTTP/1.1 200 OK \r\n\r\n" + output + "\r\n");
                    outputClient.write(s.getBytes());
                }

                catch (Exception except) {
                    outputClient.writeBytes("HTTP/1.1 404 Not found\r\n");
                }

                if (host == null || port == null) {
                    throw new IOException("Wrong port and/or host input");
                }

            } else {
                outputClient.writeBytes("HTTP/1.1 400 Bad request\r\n");
            }

            inputClient.close();
            outputClient.close();
            clientsocket.close();

        }

        catch (Exception e) {
            System.out.println("Could not run");
        }

    }

}