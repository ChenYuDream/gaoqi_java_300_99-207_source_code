package com.chenyu;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * 对文件进行MD5加密
 *
 * @author yu_chen
 * @create 2018-01-30 18:01
 **/
public class MyFileMd5 implements Runnable {


    private Logger logger = Logger.getLogger("MyFileMd5");
    /**
     * 文件过滤后的队列
     */
    private LinkedBlockingQueue<MyFile> fileFilterQueue;
    /**
     * 文件MD5的队列
     */
    private LinkedBlockingQueue<MyFile> fileMd5Queue;


    public MyFileMd5(LinkedBlockingQueue<MyFile> fileFilterQueue, LinkedBlockingQueue<MyFile> fileMd5Queue) {
        this.fileFilterQueue = fileFilterQueue;
        this.fileMd5Queue = fileMd5Queue;
    }

    @Override
    public void run() {
        while (true) {
            fileMd5();
        }
    }

    /**
     * 进行文件过滤
     */
    private void fileMd5() {
        MyFile myFile = null;
        try {
            myFile = fileFilterQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (myFile != null) {
            String md5 = MD5Util.getMD5(myFile.getFile());
            myFile.setMd5(md5);
            try {
                fileMd5Queue.put(myFile);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
