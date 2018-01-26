package com.chenyu.tcp.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 创建客户端
 * 接收数据+发送数据
 *
 * @author yu_chen
 * @create 2018-01-26 15:45
 **/
public class Client {


    public static void main(String[] args) throws IOException {
        //创建客户端 必须指定服务器+端口 此时就在连接
        Socket client = new Socket("localhost", 8888);
        //2、接收数据
        /*BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String s = br.readLine();
        System.out.println(s);*/
        DataInputStream dataInputStream=new DataInputStream(client.getInputStream());
        String s = dataInputStream.readUTF();
        System.out.println(s);

    }
}
