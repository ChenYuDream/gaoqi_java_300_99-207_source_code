package com.chenyu;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * 文件过滤线程
 *
 * @author yu_chen
 * @create 2018-01-30 18:01
 **/
public class MyFileFilter implements Runnable {

    private Logger logger = Logger.getLogger("MyFileFilter");
    /**
     * 文件过滤的后缀
     */
    private static final String FILTER_EXT = ".dll";

    private static final String FILTER_EXT2 = ".exe";
    /**
     * 原始文件队列
     */
    private LinkedBlockingQueue<MyFile> fileReaderQueue;
    /**
     * 文件过滤队列
     */
    private LinkedBlockingQueue<MyFile> fileFilterQueue;


    public MyFileFilter(LinkedBlockingQueue<MyFile> fileReaderQueue, LinkedBlockingQueue<MyFile> fileFilterQueue) {
        this.fileReaderQueue = fileReaderQueue;
        this.fileFilterQueue = fileFilterQueue;
    }

    @Override
    public void run() {
        while (true) {
            fileFilter();
        }
    }

    /**
     * 进行文件过滤
     */
    private void fileFilter() {
        MyFile myFile = null;
        try {
            myFile = fileReaderQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (myFile != null) {
            String name = myFile.getFile().getName();
            if (name.endsWith(FILTER_EXT) || name.endsWith(FILTER_EXT2)) {
                try {
                    fileFilterQueue.put(myFile);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
