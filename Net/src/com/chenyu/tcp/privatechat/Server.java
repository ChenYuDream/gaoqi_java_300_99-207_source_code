package com.chenyu.tcp.privatechat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author yu_chen
 * @create 2018-01-26 16:07
 **/
public class Server {

    private Map<String, MyChannel> myChannels = new Hashtable<>();

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
        int i = 1;
        while (true) {
            Socket client = serverSocket.accept();
            MyChannel myChannel = new MyChannel("c" + i, client);
            myChannels.put(myChannel.getId(), myChannel);
            new Thread(myChannel).start();
            i++;
        }
    }

    private class MyChannel implements Runnable {

        private String id;

        private DataInputStream dis;
        private DataOutputStream dos;
        private boolean isRunning = true;

        public MyChannel(String id, Socket client) {
            try {
                this.id = id;
                System.out.println("客户端：" + id + "已连接");
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                isRunning = false;
                myChannels.remove(this);
                CloseUtil.closeAll(dis, dos);
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
                myChannels.remove(this.id);
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
                myChannels.remove(this.id);
                CloseUtil.closeAll(dos);
            }
        }

        /**
         * 给其他人发消息
         */
        private void sendOthers() {
            String msg = receiveMsg();
            for (String key : myChannels.keySet()) {
                if (msg.contains(key)) {
                    myChannels.get(key).sendMsg(msg);
                    //有一个包含就为true
                }
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

