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

public class Server extends Thread {

    private ServerSocket serverSocket;

    public Server(int port, boolean isSecure) throws Exception {
        serverSocket = SocketFactory.getServerSocket(port, isSecure);
    }

    public void run() {
        try {
            while (true) {
                Socket receiverSocket = serverSocket.accept();
                exec(receiverSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.close(serverSocket);
        }
    }

    private void exec(Socket socket) {

        try (BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter toClient = new PrintWriter(socket.getOutputStream(), false)) {
            String command = fromClient.readLine();

            if ("server stop".equalsIgnoreCase(command)) {
                System.exit(0);
            }

            System.out.println("command: " + command);
            List<String> result = runCommand(command, "cp866"); // "windows-1251", "utf8", "cp866"

            for (String res : result) {
                toClient.println(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<String> runCommand(String command, String encoding) {
        List<String> result = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), encoding));
            BufferedReader stdError = new BufferedReader(
                    new InputStreamReader(process.getErrorStream(), encoding));

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
            e.printStackTrace(System.err);
        }
        return result;
    }

}
