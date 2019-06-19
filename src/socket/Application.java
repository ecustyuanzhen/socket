package socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

/**
 * zhenyuan
 * 2019-06-18 23:34
 */
public class Application {
    public static void main(String[] args) throws InterruptedException, IOException {

        //初始化客户端参数
        Client client = new Client(8088, "127.0.0.1");
        Random random = new Random();
        while (true) {
            int i = random.nextInt(10);
            Thread.sleep(2000);
            if (i < 4) {
                client.send("laoyaoshuai");
            } else {
                client.send("yuanzhenshuai");
            }
            client.get();
        }

    }

}
