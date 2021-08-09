package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class socket_server {

    private int port = 8888;
    private ServerSocket serverSocket;

    private void receieveFile() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("server start...");
        Socket socket = null;
        while (true) {
            try {

                socket = serverSocket.accept();
                System.out.println("wait conncet...");

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String fileName = dis.readUTF();  //获取文件名
                long  filelength=dis.readLong();  //获取长度

                DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName));
                byte[] buf = new byte[(int)filelength];
                int len = 0;
                while ((len = dis.read(buf)) != -1) {
                    dos.write(buf, 0, len);
                    dos.flush();
                }
                System.out.println(fileName+" file get ok...");
                dis.close();
                dos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    public static void main(String[] args) throws IOException {
        new socket_server().receieveFile();
    }

}