package com.chenyu.tcp.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yu_chen
 * @create 2018-01-26 16:07
 **/
public class Server {

    private List<MyChannel> myChannels = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new Server().send();
    }

    /**
     * 启动服务端
     *
     * @throws IOException
     */
    private void send() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            Socket client = serverSocket.accept();
            MyChannel myChannel = new MyChannel(client);
            myChannels.add(myChannel);
            new Thread(myChannel).start();
        }
    }

    private class MyChannel implements Runnable {

        private DataInputStream dis;
        private DataOutputStream dos;
        private boolean isRunning = true;

        public MyChannel(Socket client) {
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                isRunning = false;
                myChannels.remove(this);
                CloseUtil.closeAll(dis, dos);
            }
        }

        /**
         * 读取客户端传来的消息
         *
         * @return
         */
        private String receiveMsg() {
            String msg = "";
            try {
                msg = dis.readUTF();
                System.out.println("服务器-->" + msg);
            } catch (IOException e) {
                isRunning = false;
                myChannels.remove(this);
                CloseUtil.closeAll(dis);
            }
            return msg;
        }

        /**
         * 发送消息读取到的消息
         */
        private void sendMsg(String msg) {
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                isRunning = false;
                myChannels.remove(this);
                CloseUtil.closeAll(dos);
            }
        }

        /**
         * 给其他人发消息
         */
        private void sendOthers() {
            String msg = receiveMsg();
            for (MyChannel myChannel : myChannels) {
                myChannel.sendMsg(msg);
            }
        }


        @Override
        public void run() {
            while (isRunning) {
                sendOthers();
            }
        }
    }
}

