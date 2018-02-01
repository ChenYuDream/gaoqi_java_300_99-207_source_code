package com.chenyu;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * 客户端调用
 *
 * @author yu_chen
 * @create 2018-01-30 18:15
 **/
public class Client {

    /**
     * 核心线程数
     */
    private static final int FILE_FILTER_THREAD_NUM = 12;

    private static final int FILE_MD5_THREAD_NUM = 2;


    private static final String BASE_PATH = "C:\\Windows\\system32";
    /**
     * 主队列进行文件的读取
     */
    private LinkedBlockingQueue<MyFile> fileReaderQueue = new LinkedBlockingQueue<>();
    /**
     * 进行文件过滤的队列
     */
    private LinkedBlockingQueue<MyFile> fileFilterQueue = new LinkedBlockingQueue<>();
    /**
     * 对文件进行Md5加密的队列
     */
    private LinkedBlockingQueue<MyFile> fileMd5Queue = new LinkedBlockingQueue<>();


    public static void main(String[] args) {
        doMyFileTask();

    }

    /**
     * 进行文件读取的任务
     */
    private static void doMyFileTask() {
        Client client = new Client();
        client.startFileFilterThreadPool();
        client.startFileMd5ThreadPool();
        client.startFileWriterThread();
        client.startFileReaderThread();
    }


    /**
     * 开启文件读取的线程
     */
    private void startFileReaderThread() {
        MyFileReader myFileReader = new MyFileReader(BASE_PATH, fileReaderQueue);
        Thread thread = new Thread(myFileReader);
        thread.start();
    }

    /**
     * 开启文件过滤的线程池
     */
    private void startFileFilterThreadPool() {
        ExecutorService executor = Executors.newFixedThreadPool(FILE_FILTER_THREAD_NUM);
        for (int i = 0; i < FILE_FILTER_THREAD_NUM; i++) {
            MyFileFilter fileFilter = new MyFileFilter(fileReaderQueue, fileFilterQueue);
            executor.execute(fileFilter);
        }
    }

    /**
     * 开启文件过滤的线程池
     */
    private void startFileMd5ThreadPool() {
        ExecutorService executor = Executors.newFixedThreadPool(FILE_MD5_THREAD_NUM);
        for (int i = 0; i < FILE_MD5_THREAD_NUM; i++) {
            MyFileMd5 fileMd5 = new MyFileMd5(fileFilterQueue, fileMd5Queue);
            executor.execute(fileMd5);
        }
    }


    private void startFileWriterThread() {
        MyFileWriter myFileWriter = new MyFileWriter(fileMd5Queue);
        Thread thread = new Thread(myFileWriter);
        thread.start();
    }

}


