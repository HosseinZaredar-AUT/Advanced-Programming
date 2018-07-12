package pack.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private static Socket server;

    public Client(String ip) {
        try {
            server = new Socket(ip, 7654);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InputStream getInputStream() {
        try {
            return server.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static OutputStream getOutputStream() {
        try {
            return server.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
