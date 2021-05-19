package random;

import random.comand.Client;
import random.comand.Server;
import random.file.FileReceiver;
import random.file.FileSender;
import random.util.SslConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        try {
            if (args.length > 0) {
                int index = 0;
                String mode = args[index++];
                String[] params = Arrays.copyOfRange(args, index, args.length);

                if ("-r".equals(mode)) {
                    receive(params);
                } else if ("-t".equals(mode)) {
                    transmit(params);
                } else if ("-rs".equals(mode)) {
                    receiveSsl(params);
                } else if ("-ts".equals(mode)) {
                    transmitSsl(params);
                } else if ("-wc".equals(mode)) {
                    waitCommand(params);
                } else if ("-c".equals(mode)) {
                    command(params);
                } else {
                    help();
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            help();
        }
    }

    private static void waitCommand(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        if (args.length == 5) {
            SslConfig.setSecurity(args[1], args[2], args[3], args[4]);
        } else if (args.length == 3) {
            SslConfig.setSecurity(args[1], args[2], args[1], args[2]);
        } else {
            help();
            return;
        }
        Server server = new Server(port, true);
        server.start();
    }

    private static void command(String[] args) throws IOException {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        if (args.length == 6) {
            SslConfig.setSecurity(args[2], args[3], args[4], args[5]);
        } else if (args.length == 4) {
            SslConfig.setSecurity(args[2], args[3], args[2], args[3]);
        } else {
            help();
            return;
        }

        String command;
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter command: ");
            command = in.nextLine();

            if (command.equals("exit")) {
                break;
            }

            Client client = new Client(ip, port, true);
            client.sendCommand(command);
        }
    }

    private static void receive(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        FileReceiver fs = new FileReceiver(port, false);
        fs.start();
    }

    private static void transmit(String[] args) throws IOException {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        String file = args[2];
        FileSender fs = new FileSender(ip, port, false);
        fs.sendFile(file);
    }

    private static void receiveSsl(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        if (args.length == 5) {
            SslConfig.setSecurity(args[1], args[2], args[3], args[4]);
        } else if (args.length == 3) {
            SslConfig.setSecurity(args[1], args[2], args[1], args[2]);
        } else {
            help();
            return;
        }
        FileReceiver fs = new FileReceiver(port, true);
        fs.start();
    }

    private static void transmitSsl(String[] args) throws IOException {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        String file;
        if (args.length == 7) {
            file = args[6];
            SslConfig.setSecurity(args[2], args[3], args[4], args[5]);
        } else if (args.length == 5) {
            file = args[4];
            SslConfig.setSecurity(args[2], args[3], args[2], args[3]);
        } else {
            help();
            return;
        }
        FileSender fs = new FileSender(ip, port, true);
        fs.sendFile(file);
    }

    private static void help() {
        System.out.println("transfer.jar -r RECEIVER_PORT FILE_NAME\treceiver mode");
        System.out.println("transfer.jar -t TRANSMITTER_PORT RECEIVER_IP FILE_NAME\ttransmitter mode");
        System.out.println("transfer.jar -h\thelp");
    }

}
