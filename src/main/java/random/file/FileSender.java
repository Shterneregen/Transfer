package random.file;

import random.util.SocketFactory;
import random.util.Utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class FileSender {

    private final int port;
    private final Socket socket;

    public FileSender(String host, int port, boolean isSecure) throws IOException {
        System.out.println("SSL: " + isSecure);
        this.port = port;
        socket = SocketFactory.getClientSocket(host, port, isSecure);
    }

    public void sendFile(String filePath) throws IOException {
        System.out.println("Sending file " + filePath + " on port " + port);
        try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             FileInputStream fis = new FileInputStream(filePath)) {
            File file = getFile(filePath);
            if (file == null) {
                return;
            }

            byte[] buffer = new byte[4096];
            String fileName = file.getName();
            System.out.println("File name: " + fileName);
            dos.writeUTF(fileName);

            long fileSize = file.length();
            System.out.println("File size: " + fileSize + " bytes");
            dos.writeLong(file.length());

            while (fis.read(buffer) > 0) {
                dos.write(buffer);
            }
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            Utils.close(socket);
        }
    }

    private File getFile(String filePath) {
        File file;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File does not exist!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("File not found!");
            return null;
        }
        return file;
    }

}
