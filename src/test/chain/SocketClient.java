package test.chain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author zouxiaobang
 * @date 2021/1/30
 */
public class SocketClient {
    private Socket socket;
    @Before
    public void createSocket() throws IOException {
        socket = new Socket("localhost", 8090);
    }

    @Test
    public void run() {
        String word = "Hello";
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(word.getBytes());
            InputStream inputStream = socket.getInputStream();
            int ch = 0;
            byte[] buff = new byte[1024];
            ch = inputStream.read(buff);
            String content = new String(buff, 0, ch);

            Assert.assertEquals(content, word);

            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
