package com.chenyu;

import java.util.Queue;

/**
 * 文件过滤线程
 *
 * @author yu_chen
 * @create 2018-01-30 18:01
 **/
public class MyFileFilter implements Runnable {


    /**
     * 文件过滤的后缀
     */
    private static final String FILTER_EXT = ".dll";
    /**
     * 原始文件队列
     */
    private Queue<MyFile> fileQueue;
    /**
     * 文件过滤队列
     */
    private Queue<MyFile> fileFilterQueue;
    /**
     * 线程停止的标记
     */
    private boolean flag = true;

    private int threadNum;


    /**
     * 设置线程结束标识
     *
     * @param flag
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public MyFileFilter(Queue<MyFile> fileQueue, Queue<MyFile> fileFilterQueue, int threadNum) {
        this.fileQueue = fileQueue;
        this.fileFilterQueue = fileFilterQueue;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {
        System.out.println("fileFilter子线程[" + threadNum + "]开启");
        while (flag) {
            fileFilter();
        }
        System.out.println("fileFilter子线程[" + threadNum + "]关闭");
    }

    /**
     * 进行文件过滤
     */
    private void fileFilter() {
        MyFile myFile = fileQueue.poll();
        if (myFile != null) {
            if (myFile.getFile().getName().endsWith(FILTER_EXT)) {
                System.out.println("过滤线程取出并添加入过滤队列文件：" + myFile.getFile().getName());
                fileFilterQueue.offer(myFile);
            }
        }
    }

}
