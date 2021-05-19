package random.util;

public class SslConfig {

    private SslConfig() {
    }

    public static void setSecurity(
            String keyStorePath, String keyStorePsw, String trustStorePath, String trustStorePsw) {
        System.setProperty("javax.net.ssl.keyStore", keyStorePath);
        System.setProperty("javax.net.ssl.keyStorePassword", keyStorePsw);
        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePsw);
        System.out.println("Security settings configured");
    }
}
