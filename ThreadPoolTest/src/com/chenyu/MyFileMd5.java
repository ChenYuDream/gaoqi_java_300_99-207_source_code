package com.chenyu;

import java.util.Queue;

/**
 * 对文件进行MD5加密
 *
 * @author yu_chen
 * @create 2018-01-30 18:01
 **/
public class MyFileMd5 implements Runnable {


    /**
     * 文件过滤后的队列
     */
    private Queue<MyFile> fileFilterQueue;
    /**
     * 文件MD5的队列
     */
    private Queue<MyFile> fileMd5Queue;
    /**
     * 线程停止的标记
     */
    private boolean flag = true;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public MyFileMd5(Queue<MyFile> fileFilterQueue, Queue<MyFile> fileMd5Queue) {
        this.fileFilterQueue = fileFilterQueue;
        this.fileMd5Queue = fileMd5Queue;
    }

    @Override
    public void run() {
        while (flag) {
            fileMd5();
        }
    }

    /**
     * 进行文件过滤
     */
    private void fileMd5() {
        MyFile myFile = fileFilterQueue.poll();
        if (myFile != null) {
            String md5 = MD5Util.getMD5(myFile.getFile());
            myFile.setMd5(md5);
            fileMd5Queue.offer(myFile);
            System.out.println("MD5线程进行文件加密：" + myFile.getFile().getName());
        }
    }

}
