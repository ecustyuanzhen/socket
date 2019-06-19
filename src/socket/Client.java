package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * zhenyuan
 * 2019-06-18 23:27
 */
public class Client {
    private int port;
    private String host;
    private DataOutputStream out;
    private DataInputStream in ;

    public Socket getClient() {
        return client;
    }

    private static Socket client = null;

    public Client(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void start() {
        try {
            client = new Socket(host, port);
            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int send(String s) {
        int flag = 0;
        if (null == client) {
            start();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                flag = -1;
            }
        }
        try {
            genProtocol(out, s);
        } catch (IOException e) {
            e.printStackTrace();
            flag = -1;
        }

        return flag;
    }

    public int get(){
        int flag = 0;
        while (null == in){

        }
        try {
            byte[] lenByte = new byte[4];
            in.read(lenByte, 0, 4);
            int totalLen = ProtocolHelper.ntoh1(lenByte);             //读取消息长度
            byte[] data = new byte[totalLen];     //定义存放消息内容的字节数组
            //in.readUTF(data);                      //读取消息内容
            //String msg = new String(data);            //消息内容
            String msg = in.readUTF();
            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
            flag = -1;
        }

        return flag;
    }

    /**
     * 构造协议
     *
     * @param out
     * @param msg
     * @throws IOException
     */
    public static void genProtocol(DataOutputStream out, String msg) throws IOException {
        byte[] result = msg.getBytes("GB2312");         //消息内容

        byte[] length = ProtocolHelper.htonl(result.length);   //消息长度
        out.write(length);                      //写入消息长度
        out.write(result);                      //写入消息内容

        out.flush();
    }


}
