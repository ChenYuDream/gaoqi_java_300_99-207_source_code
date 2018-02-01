package com.chenyu;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * 文件输出线程
 *
 * @author yu_chen
 * @create 2018-01-30 17:48
 **/
public class MyFileWriter implements Runnable {

    private Logger logger = Logger.getLogger("MyFileWriter");
    /**
     * 文件队列
     */
    private LinkedBlockingQueue<MyFile> fileMd5Queue;

    public MyFileWriter(LinkedBlockingQueue<MyFile> fileMd5Queue) {
        this.fileMd5Queue = fileMd5Queue;
    }

    @Override
    public void run() {
        while (true) {
            writeFile();
        }
    }

    private void writeFile() {
        //取出一个元素
        MyFile file = null;
        try {
            file = fileMd5Queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (file != null) {
            logger.info(String.format("[%s]file=%s,md5=%s", Thread.currentThread().getName(), file.getFile().getAbsolutePath(), file.getMd5()));
        }
    }
}
