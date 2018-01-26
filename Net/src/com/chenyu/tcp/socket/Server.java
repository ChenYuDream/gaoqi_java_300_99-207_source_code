package com.chenyu.tcp.socket;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 创建服务器
 *
 * @author yu_chen
 * @create 2018-01-26 15:45
 **/
public class Server {

    public static void main(String[] args) throws IOException {
        //1、创建服务端 指定端口 serverSocket(int port)
        ServerSocket serverSocket = new ServerSocket(8888);
        //2、接收客户端连接 阻塞式
        Socket socket = serverSocket.accept();
        System.out.println("一个客户端建立连接");
        //3、发送数据
        String msg = "欢迎使用";
       /* BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(msg);
        bw.newLine();
        bw.flush();*/
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(msg);
    }

}
