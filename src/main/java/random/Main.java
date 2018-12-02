package random;

public class Main {

    private static int DEF_PORT = 47372;

    public static void main(String[] args) {
        if (args.length > 0) {
            if ("-r".equals(args[0])) {
                // receiver
                int port = args.length > 1
                        ? Integer.parseInt(args[1])
                        : DEF_PORT;
                FileReceiver fs = new FileReceiver(port);
                fs.start();
            } else if ("-s".equals(args[0])) {
                // sender
                if (args.length > 2) {
                    int port = Integer.parseInt(args[1]);
                    String file = args[2];
                    FileSender fc = new FileSender("localhost", port, file);
                } else if (args.length > 1) {
                    String file = args[1];
                    FileSender fc = new FileSender("localhost", DEF_PORT, file);
                }
            }
        }
    }
}
