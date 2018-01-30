package com.chenyu;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private static final int CORE_POOL_SIZE = 8;

    /**
     * 最大线程数
     */
    private static final int maximumPoolSize = 32;

    /**
     * 线程空闲状态下存活时间
     */
    private static final int keepAliveTime = 1;

    /**
     * 时间的单位
     */
    private static final TimeUnit timeUnit = TimeUnit.SECONDS;


    private static final String BASE_PATH = "C:\\Windows\\Branding";
    /**
     * 主队列进行文件的读取
     */
    private Queue<MyFile> myFileQueue = new LinkedBlockingQueue<>();
    /**
     * 进行文件过滤的队列
     */
    private Queue<MyFile> fileFilterQueue = new LinkedBlockingQueue<>();
    /**
     * 对文件进行Md5加密的队列
     */
    private Queue<MyFile> fileMd5Queue = new LinkedBlockingQueue<>();


    public static void main(String[] args) {
        doMyFileTask();

    }

    /**
     * 进行文件读取的任务
     */
    private static void doMyFileTask() {
        Client client = new Client();
        MyFileReader myFileReader = client.startFileReaderThread();
        ThreadPoolExecutor filterThreadPool = client.startFileFilterThreadPool();
        ThreadPoolExecutor md5ThreadPool = client.startFileMd5ThreadPool();
        MyFileWriter myFileWriter = client.startFileWriterThread();

        while (true) {
            if (myFileReader.isFinish()) {
                System.out.println("文件读取完毕");
                break;
            }
            //当文件读取没有完毕时main线程一直阻塞
        }
        filterThreadPool.shutdown();
        md5ThreadPool.shutdown();
        //myFileWriter.setFlag(false);
        /*while (true) {
            System.out.println("判断文件过滤线程池");
            if (filterThreadPool.isTerminated()) {
                System.out.println("文件过滤线程执行完毕");
                break;
            }
            //判断是否过滤的线程池已经完毕
        }
        md5ThreadPool.shutdown();
        while (true) {
            System.out.println("判断md5线程池线程池");
            if (md5ThreadPool.isTerminated()) {
                System.out.println("md5线程池执行完毕");
                myFileWriter.setFlag(false);
                break;
            }
            //判断是否过滤的线程池已经完毕
        }*/

    }


    /**
     * 开启文件读取的线程
     */
    private MyFileReader startFileReaderThread() {
        MyFileReader myFileReader = new MyFileReader(BASE_PATH, myFileQueue);
        Thread thread = new Thread(myFileReader, "fileReaderThread");
        thread.start();
        return myFileReader;
    }

    /**
     * 开启文件过滤的线程池
     */
    private ThreadPoolExecutor startFileFilterThreadPool() {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, maximumPoolSize, keepAliveTime, timeUnit, blockingQueue);
        for (int i = 0; i < 1000; i++) {
            MyFileFilter fileFilter = new MyFileFilter(myFileQueue, fileFilterQueue,i);
            executor.execute(fileFilter);
        }
        return executor;
    }

    /**
     * 开启文件过滤的线程池
     */
    private ThreadPoolExecutor startFileMd5ThreadPool() {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, maximumPoolSize, keepAliveTime, timeUnit, blockingQueue);
        for (int i = 0; i < 1000; i++) {
            MyFileMd5 fileMd5 = new MyFileMd5(fileFilterQueue, fileMd5Queue);
            executor.execute(fileMd5);
        }
        return executor;
    }


    private MyFileWriter startFileWriterThread() {
        MyFileWriter myFileWriter = new MyFileWriter(fileMd5Queue);
        Thread thread = new Thread(myFileWriter);
        thread.start();
        return myFileWriter;
    }

}


