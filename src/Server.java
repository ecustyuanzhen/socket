import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * zhenyuan
 * 2019-06-17 22:33
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8080);
            while (true) {
                Socket client = server.accept();
                System.out.println("客户端" + client.getRemoteSocketAddress() + "连接成功");
                parseProtocol(client);
            }
        } catch (IOException e) {
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

        out.writeUTF("hello");
        out.flush();
//        //协议解析
//        while (true) {
//            byte[] lenByte = new byte[4];
//            dis.read(lenByte, 0, 4);
//            int totalLen = ntoh1(lenByte);             //读取消息长度
//            byte[] data = new byte[totalLen];     //定义存放消息内容的字节数组
//            dis.readFully(data);                      //读取消息内容
//            String msg = new String(data);            //消息内容
//
//            System.out.println("接收消息长度" + totalLen);
//            System.out.println("发来的内容是:" + msg);
//            out.write(msg.getBytes());
//            out.flush();
//        }
    }

    private static int ntoh1(byte[] data) {
        return ((int) data[0] << 24) | (((int) data[1] << 16) & 0xffffff) | (((int) data[2] << 8) & 0xffff) | ((int) data[3] & 0xff);
    }

}
