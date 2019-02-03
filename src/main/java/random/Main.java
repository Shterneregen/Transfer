package random;

import java.io.IOException;
import java.util.Arrays;

public class Main {

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
                } else if ("-h".equals(mode)
                        || "--help".equals(mode)
                        || "/h".equals(mode)
                        || "/?".equals(mode)
                        || "?".equals(mode)) {
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
        FileReceiver fs = new FileReceiver(port);
        fs.start();
    }

    private static void transmit(String[] args) throws IOException {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        String file = args[2];
        FileSender fc = new FileSender(ip, port, file);
    }

    private static void help() {
        System.out.println("transfer.jar -r RECEIVER_PORT FILE_NAME\treceiver mode");
        System.out.println("transfer.jar -t TRANSMITTER_PORT RECEIVER_IP FILE_NAME\ttransmitter mode");
        System.out.println("transfer.jar -h\thelp");
    }
}
