package com.chenyu.tcp.privatechat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 输出数据的线程
 *
 * @author yu_chen
 * @create 2018-01-26 16:18
 **/
public class MyWriter implements Runnable {
    /**
     * 控制台输入流
     */
    private BufferedReader console;
    /**
     * 管道输出流
     */
    private DataOutputStream dos;

    /**
     * 是否正处在运行
     */
    private boolean isRunning = true;

    public MyWriter() {
        console = new BufferedReader(new InputStreamReader(System.in));
    }

    public MyWriter(Socket client) {
        this();
        try {
            dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            isRunning = false;
            CloseUtil.closeAll(dos, console);
        }
    }

    /**
     * //从控制台接收
     *
     * @return
     */
    private String getMsgFromConsole() {
        try {
            return console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 1、从控制台接收数据
     * 2、
     */
    public void send() {
        String msg = getMsgFromConsole();
        if (msg != null && msg != "") {
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                isRunning = false;
                CloseUtil.closeAll(dos, console);
            }
        }

    }

    @Override
    public void run() {
        while (isRunning) {
            send();
        }
    }
}
