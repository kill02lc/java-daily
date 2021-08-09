package socket;


import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class socket_client {

    private String SERVER_IP ="";
    private int SERVER_PORT =0;
    private String FILE_PATH ="";

    private Socket client;

    private FileInputStream fis;

    private DataOutputStream dos;

    public socket_client(String ip,int port,String path) throws Exception {
        this.SERVER_IP=ip;
        this.SERVER_PORT=port;
        this.FILE_PATH=path;
        Socket sock = new Socket(this.SERVER_IP, this.SERVER_PORT);
        this.client = sock;
        System.out.println("client port:" + client.getLocalPort() + " conncet server");
    }
    public void sendFile() throws Exception {
        try {
            File file = new File(FILE_PATH);
            if(file.exists()) {
                fis = new FileInputStream(file);
                dos = new DataOutputStream(client.getOutputStream());

                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();

                int a=100;

                System.out.println("start send file");
                byte[] bytes = new byte[(int)file.length()];
                int length = 0;
                long progress = 0;
                while((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    dos.write(bytes, 0, length);
                    dos.flush();
                    progress += length;
                    System.out.print("| " + (100*progress/file.length()) + "% |");
                }
                System.out.println("send file ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null)
                fis.close();
            if(dos != null)
                dos.close();
            client.close();
        }
    }

    public static void main(String[] args) {
        try {
            //接收参数
            Scanner sc = new Scanner(System.in);
            System.out.println("please you input ip:");
            String ip = sc.nextLine();
            System.out.println("please you input port:");
            String port = sc.nextLine();
            System.out.println("please you input filepath:");
            String filepath = sc.nextLine();

            socket_client client = new socket_client(ip,Integer.valueOf(port).intValue(),filepath);// 启动客户端连接
            client.sendFile(); // 传输文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}