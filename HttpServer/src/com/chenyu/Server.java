package com.chenyu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 *
 * @author yu_chen
 * @create 2018-01-26 18:12
 **/
public class Server {
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        server.receive();
    }

    /**
     * 启动
     */
    private void start() {
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 接收
     */
    private void receive() {
        try {
            Socket client = serverSocket.accept();
            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String msg;
            while ((msg = br.readLine()).length() > 0) {
                sb.append(msg);
                sb.append("\r\n");
            }
            System.out.println(sb.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
