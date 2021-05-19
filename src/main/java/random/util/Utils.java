package random.util;

import java.io.Closeable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    private Utils() {
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
