package random;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class FileSender {

    private Socket s;

    public FileSender(String host, int port, String file) {
        try {
            s = new Socket(host, port);
            sendFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String file) throws IOException {
        File f = new File(file);
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];

        String fileName = f.getName();
        System.out.println("fileName: " + fileName);
        dos.writeUTF(fileName);

        long fileSize = f.getTotalSpace();
        System.out.println("fileSize: " + fileSize);
        dos.writeLong(f.length());

        while (fis.read(buffer) > 0) {
            dos.write(buffer);
        }

        fis.close();
        dos.close();
    }

}
