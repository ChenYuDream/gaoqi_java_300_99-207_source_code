package com.chenyu;


import java.io.File;
import java.util.Queue;

/**
 * 文件读取线程
 * 创建文件并将文件写入队列中
 *
 * @author yu_chen
 * @create 2018-01-30 17:21
 **/
public class MyFileReader implements Runnable {

    /**
     * 基础遍历路径
     */
    private String basePath;
    /**
     * 文件队列
     */
    private Queue<MyFile> fileContainer;

    private boolean finish = false;

    public boolean isFinish() {
        return finish;
    }

    public MyFileReader(String basePath, Queue<MyFile> fileContainer) {
        this.basePath = basePath;
        this.fileContainer = fileContainer;
    }

    @Override
    public void run() {
        File file = new File(basePath);
        readFile(file);
        finish = true;
    }

    /**
     * 递归读取文件并放入
     *
     * @param file
     */
    private void readFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    readFile(file1);
                }
            }
        } else {
            MyFile myFile = new MyFile("", file);
            //否则就将文件加入队列
            System.out.println("读取线程读取文件：" + file.getName());
            fileContainer.offer(myFile);
        }

    }
}
