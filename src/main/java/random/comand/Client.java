package random.comand;

import random.util.SocketFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static final Logger LOG = Logger.getLogger(Client.class.getName());

    private Socket socket;

    public Client(String host, int port, boolean isSecure) throws IOException {
        socket = SocketFactory.getClientSocket(host, port, isSecure);
    }

    public void sendCommand(String command) {
        try (PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            toServer.println(command);

            String line;
            while ((line = fromServer.readLine()) != null) {
                System.out.println(line);
            }
            socket.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
