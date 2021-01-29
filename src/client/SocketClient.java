package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author zouxiaobang
 * @date 2021/1/29
 */
public class SocketClient {
    public static void main(String[] args) {
        try (Socket socket = createSocket()) {
            write(socket);
//            read(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void read(Socket socket) {
        try(InputStream inputStream = socket.getInputStream()) {
            int ch = 0;
            byte[] buff = new byte[1024];
            ch = inputStream.read(buff);
            String content = new String(buff, 0, ch);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void write(Socket socket) {
        try (OutputStream outputStream = socket.getOutputStream()) {
            outputStream.write("hello".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static Socket createSocket() throws IOException {
        return new Socket("localhost", 8090);
    }
}
