package com.chenyu.tcp.privatechat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 读取数据的线程
 *
 * @author yu_chen
 * @create 2018-01-26 16:15
 **/
public class MyReader implements Runnable {

    private DataInputStream dis;

    private boolean isRunning = true;

    public MyReader() {
    }

    public MyReader(Socket client) {
        try {
            this.dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            isRunning = false;
            CloseUtil.closeAll(dis);

        }
    }

    private String receive() {
        String msg = "";
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            isRunning = false;
            CloseUtil.closeAll(dis);
        }
        return msg;
    }

    @Override
    public void run() {
        while (isRunning) {
            System.out.println(receive());
        }
    }
}
