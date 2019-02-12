package random;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileReceiver extends Thread {

    private ServerSocket serverSocket;

    public FileReceiver(int port, boolean isSecure) throws Exception {
        if (isSecure) {
            SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            serverSocket = sslServerSocketfactory.createServerSocket(port);
        } else {
            serverSocket = new ServerSocket(port);
        }
        System.out.println("SSL: " + isSecure);
        System.out.println("Ready to receive on port " + port + "\n");
    }

    public void run() {
        while (true) {
            try {
                Socket receiverSocket = serverSocket.accept();
                saveFile(receiverSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(Socket receiverSocket) throws IOException {
        DataInputStream dis = null;
        FileOutputStream fos = null;
        try {
            dis = new DataInputStream(receiverSocket.getInputStream());
            byte[] buffer = new byte[4096];
            String fileName = dis.readUTF();
            long fileSize = dis.readLong();

            System.out.println("File reception");
            System.out.println("File name: " + fileName);
            System.out.println("File size: " + fileSize + " bytes");

            fos = new FileOutputStream(genName(fileName, 0));

            int count = 1;
            int read = 0;
            int remaining = (int) fileSize;
            while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                remaining -= read;

                System.out.print(".");
                if (count % 50 == 0) {
                    System.out.println();
                }
                fos.write(buffer, 0, read);
                count++;
            }
            System.out.println();
            System.out.println("Reception complete");

        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            Utils.close(fos);
            Utils.close(dis);
            Utils.close(receiverSocket);
        }
    }

    private String genName(String fileName, int index) {
        File tmpDir = new File(fileName);
        boolean exists = tmpDir.exists();
        if (exists) {
            String ext = getFileExtension(fileName);
            String name = String.format("%s.%s", index, ext);
            return genName(name, ++index);
        } else {
            return fileName;
        }
    }

    private String getFileExtension(String name) {
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf + 1);
    }

}
