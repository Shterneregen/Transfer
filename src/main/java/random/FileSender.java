package random;

import javax.net.ssl.SSLSocketFactory;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class FileSender {

    private Socket socket;

    public FileSender(String host, int port, boolean isSecure, String file) throws IOException {
        System.out.println("SSL: " + isSecure);
        System.out.println("Sending file " + file + " on port " + port);

        if (isSecure) {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            socket = sslsocketfactory.createSocket(host, port);
        } else {
            socket = new Socket(host, port);
        }
        sendFile(file);
    }

    public void sendFile(String file) throws IOException {
        File f;
        try {
            f = new File(file);
            if (!f.exists()) {
                System.out.println("File does not exist!");
                return;
            }
        } catch (Exception e) {
            System.out.println("File not found!");
            return;
        }
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];

        String fileName = f.getName();
        System.out.println("File name: " + fileName);
        dos.writeUTF(fileName);

        long fileSize = f.length();
        System.out.println("File size: " + fileSize);
        dos.writeLong(f.length());

        while (fis.read(buffer) > 0) {
            dos.write(buffer);
        }

        fis.close();
        dos.close();
        socket.close();
    }

}
