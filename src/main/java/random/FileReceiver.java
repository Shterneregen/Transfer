package random;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileReceiver extends Thread {

    private ServerSocket ss;

    public FileReceiver(int port) {
        try {
            ss = new ServerSocket(port);
            System.out.println("Ready to receive on port " + port + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                Socket clientSock = ss.accept();
                saveFile(clientSock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(Socket clientSock) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        byte[] buffer = new byte[4096];
        String fileName = dis.readUTF();
        long fileSize = dis.readLong();

        System.out.println("File reception");
        System.out.println("File name: " + fileName);
        System.out.println("File size: " + fileSize);

        FileOutputStream fos = new FileOutputStream("t_" + fileName);

        int count = 1;
        int read = 0;
        int totalRead = 0;
        int remaining = (int) fileSize;
        while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.print(".");
            if (count % 50 == 0) {
                System.out.println();
            }
//            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
            count++;
        }
        System.out.println();
        System.out.println("Reception complete");
        fos.close();
        dis.close();
    }

}
