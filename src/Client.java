import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * zhenyuan
 * 2019-06-17 22:33
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("127.0.0.1", 8080);
            OutputStream out = client.getOutputStream();
            DataOutputStream outs = new DataOutputStream(out);
            while (true) {
                Scanner scaner = new Scanner(System.in);
                genProtocol(outs, scaner.next());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造协议
     *
     * @param out
     * @param msg
     * @throws IOException
     */
    private static void genProtocol(DataOutputStream out, String msg) throws IOException {
        byte[] bytes = msg.getBytes();         //消息内容
        int totalLen = 4 + bytes.length;   //消息长度

        out.writeInt(totalLen);                //写入消息长度
        out.write(bytes);                      //写入消息内容

        out.flush();
    }
}
