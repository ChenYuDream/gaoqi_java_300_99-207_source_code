package com.chenyu.tcp.chat;

import java.io.*;
import java.net.Socket;

/**
 * 客户端
 * 发送数据+接收数据
 * 写出数据，读取数据
 * 输入流和输出流在同一个县城内
 *
 * @author yu_chen
 * @create 2018-01-26 16:07
 **/
public class Client {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost", 9999);

        new Thread(new MyWriter(client)).start();

        new Thread(new MyReader(client)).start();


    }
}
