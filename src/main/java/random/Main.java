package random;

import com.sun.net.ssl.internal.ssl.Provider;

import java.io.IOException;
import java.security.Security;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        try {

            if (args.length > 0) {
                int index = 0;
                String mode = args[index++];
                String[] params = Arrays.copyOfRange(args, index, args.length);

                if ("-r".equals(mode)) {
                    Security.addProvider(new Provider());
                    receive(params);
                } else if ("-t".equals(mode)) {
                    transmit(params);
                } else if ("-rs".equals(mode)) {
                    receiveSsl(params);
                } else if ("-ts".equals(mode)) {
                    transmitSsl(params);
                } else {
                    help();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            help();
        }
    }

    private static void receive(String[] args) throws Exception {
        int port = Integer.parseInt(args[0]);
        FileReceiver fs = new FileReceiver(port, false);
        fs.start();
    }

    private static void transmit(String[] args) throws IOException {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        String file = args[2];
        FileSender fc = new FileSender(ip, port, false, file);
    }

    private static void receiveSsl(String[] args) throws Exception {
        int port = Integer.parseInt(args[0]);
        String keyStorePath, keyStorePsw, trustStorePath, trustStorePsw;
        if (args.length == 5) {
            keyStorePath = args[1];
            keyStorePsw = args[2];
            trustStorePath = args[3];
            trustStorePsw = args[4];
        } else if (args.length == 3) {
            keyStorePath = args[1];
            keyStorePsw = args[2];
            trustStorePath = args[1];
            trustStorePsw = args[2];
        } else {
            return;
        }
        setSecurity(keyStorePath, keyStorePsw, trustStorePath, trustStorePsw);
        FileReceiver fs = new FileReceiver(port, true);
        fs.start();
    }

    private static void transmitSsl(String[] args) throws IOException {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        String keyStorePath, keyStorePsw, trustStorePath, trustStorePsw, file;
        if (args.length == 7) {
            keyStorePath = args[2];
            keyStorePsw = args[3];
            trustStorePath = args[4];
            trustStorePsw = args[5];
            file = args[6];
        } else if (args.length == 5) {
            keyStorePath = args[2];
            keyStorePsw = args[3];
            trustStorePath = args[2];
            trustStorePsw = args[3];
            file = args[4];
        } else {
            return;
        }
        setSecurity(keyStorePath, keyStorePsw, trustStorePath, trustStorePsw);
        FileSender fc = new FileSender(ip, port, true, file);
    }

    private static void setSecurity(String keyStorePath, String keyStorePsw,
                                    String trustStorePath, String trustStorePsw) {
        System.setProperty("javax.net.ssl.keyStore", keyStorePath);
        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePsw);
        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePsw);
    }

    private static void help() {
        System.out.println("transfer.jar -r RECEIVER_PORT FILE_NAME\treceiver mode");
        System.out.println("transfer.jar -t TRANSMITTER_PORT RECEIVER_IP FILE_NAME\ttransmitter mode");
        System.out.println("transfer.jar -h\thelp");
    }

}
