package random.comand;

import random.util.SocketFactory;
import random.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {

    private static final Logger LOG = Logger.getLogger(Server.class.getName());

    private final ServerSocket serverSocket;
    private boolean stop = false;

    public Server(int port, boolean isSecure) throws IOException {
        serverSocket = SocketFactory.getServerSocket(port, isSecure);
    }

    @Override
    public void run() {
        try {
            while (!stop) {
                Socket receiverSocket = serverSocket.accept();
                exec(receiverSocket);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            Utils.close(serverSocket);
        }
    }

    private void exec(Socket socket) {

        try (BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter toClient = new PrintWriter(socket.getOutputStream(), false)) {
            String command = fromClient.readLine();

            if ("server stop".equalsIgnoreCase(command)) {
                stop = true;
                System.out.println("Server stopping");
                return;
            }

            System.out.println("command: " + command);
            List<String> result = runCommand(command, Encoding.CP_866);

            for (String res : result) {
                toClient.println(res);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    private static List<String> runCommand(String command, String encoding) {
        List<String> result = new ArrayList<>();
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        try {
            Process process = Runtime.getRuntime().exec(command);
            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream(), encoding));
            stdError = new BufferedReader(new InputStreamReader(process.getErrorStream(), encoding));

            String s;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                result.add(s);
            }

            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
                result.add(s);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            Utils.close(stdInput);
            Utils.close(stdError);
        }
        return result;
    }

}
