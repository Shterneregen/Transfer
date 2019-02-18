package random.util;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketFactory {

    public static ServerSocket getServerSocket(int port, boolean isSecure) throws IOException {
        ServerSocket serverSocket;
        if (isSecure) {
            SSLServerSocketFactory sslServerSocketfactory
                    = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            serverSocket = sslServerSocketfactory.createServerSocket(port);
        } else {
            serverSocket = new ServerSocket(port);
        }
        return serverSocket;
    }

    public static Socket getClientSocket(String host, int port, boolean isSecure) throws IOException {
        Socket socket;
        if (isSecure) {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            socket = sslsocketfactory.createSocket(host, port);
        } else {
            socket = new Socket(host, port);
        }
        socket.setSoTimeout(15000);
        return socket;
    }

}
