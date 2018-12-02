package random;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            if ("-s".equals(args[0])) {
                FileReceiver fs = new FileReceiver(1988);
                fs.start();
            } else if ("-c".equals(args[0])) {
                if (args.length > 1){
                    String file = args[1];
                    FileSender fc = new FileSender("localhost", 1988, file);
                }
            }
        }
    }
}
