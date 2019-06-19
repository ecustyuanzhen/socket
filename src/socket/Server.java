package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * zhenyuan
 * 2019-06-18 23:25
 */
public class Server extends Thread {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            Thread.sleep(100);
            while (true) {
                Socket client = server.accept();
                System.out.println("客户端" + client.getRemoteSocketAddress() + "连接成功");
                parseProtocol(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息解析
     *
     * @param client
     * @throws IOException
     */
    private static void parseProtocol(Socket client) throws IOException {
        InputStream is = client.getInputStream();
        DataInputStream dis = new DataInputStream(is); //读取Java标准数据类型的输入流
        DataOutputStream out = new DataOutputStream(client.getOutputStream());

        //协议解析
        while (true) {
            byte[] lenByte = new byte[4];
            dis.read(lenByte, 0, 4);
            int totalLen = ProtocolHelper.ntoh1(lenByte);             //读取消息长度
            byte[] data = new byte[totalLen];     //定义存放消息内容的字节数组
            dis.readFully(data);                      //读取消息内容
            String msg = new String(data);            //消息内容

            System.out.println("接收消息长度" + totalLen);
            System.out.println("发来的内容是:" + msg);
            if (totalLen > 11) {
                msg = "i am sure 袁振帅";
            } else {
                msg = "i am sure 老姚帅";
            }
            byte[] bytes = ProtocolHelper.htonl(msg.length());
            out.write(bytes);
            //data = msg.getBytes("GB2312");
            out.writeUTF(msg);


        }
    }

    public static void main(String[] args) {
        new Server(8088).start();
    }

}
