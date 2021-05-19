package random.file;

import random.util.SocketFactory;
import random.util.Utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileReceiver extends Thread {

    private static final Logger LOG = Logger.getLogger(FileReceiver.class.getName());

    private final ServerSocket serverSocket;
    private volatile boolean stop = false;

    public FileReceiver(int port, boolean isSecure) throws IOException {
        serverSocket = SocketFactory.getServerSocket(port, isSecure);
        System.out.println("SSL: " + isSecure);
        System.out.println("Ready to receive on port " + port + "\n");
    }

    @Override
    public void run() {
        System.out.println("File receiver started");
        Socket receiverSocket = null;
        try {
            while (!stop) {
                try {
                    receiverSocket = serverSocket.accept();
                    saveFile(receiverSocket);
                } catch (IOException e) {
                    LOG.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        } finally {
            System.out.println("Closing file receiver socket");
            Utils.close(receiverSocket);
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

            fos = new FileOutputStream(getName(fileName, 0));

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

    private String getName(String fileName, int index) {
        File tmpDir = new File(fileName);
        boolean exists = tmpDir.exists();
        if (exists) {
            String ext = getFileExtension(fileName);
            String name = String.format("%s.%s", index, ext);
            return getName(name, ++index);
        } else {
            return fileName;
        }
    }

    private String getFileExtension(String name) {
        int lastIndexOf = name.lastIndexOf('.');
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf + 1);
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

}
