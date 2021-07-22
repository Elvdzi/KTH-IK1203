import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(args[0]);
        ServerSocket welcomeSocket = new ServerSocket(port);

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            // connectionSocket.setSoTimeout(2000);

            BufferedReader inputClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outputClient = new DataOutputStream(connectionSocket.getOutputStream());

            String sentence = "";

            try {
                String s = "HTTP/1.1 200 OK \r\n\r\n";
                outputClient.write(s.getBytes());

                while ((sentence = inputClient.readLine()) != null && sentence.length() != 0) {
                    // skriver ut i terminalen
                    System.out.println(sentence);
                    sentence += "\r\n";

                    // skriver till browser (klienten), echo
                    outputClient.write(sentence.getBytes());

                }
            } catch (IOException e) {
                connectionSocket.close();

            }
            inputClient.close();
            connectionSocket.close();
            outputClient.close();
        }

    }
}
