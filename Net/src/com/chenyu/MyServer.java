package com.chenyu;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 服务端
 *
 * @author yu_chen
 * @create 2018-01-26 15:15
 **/
public class MyServer {

    public static void main(String[] args) throws IOException {

        //创建服务端+端口
        DatagramSocket server = new DatagramSocket(8888);
        //准备接收容器
        byte[] bytes = new byte[1024];
        //封装成包
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);

        server.receive(datagramPacket);

    }

}
