package com.chenyu;

import java.io.File;
import java.util.Queue;

/**
 * 文件输出线程
 *
 * @author yu_chen
 * @create 2018-01-30 17:48
 **/
public class MyFileWriter implements Runnable {

    /**
     * 文件队列
     */
    private Queue<MyFile> fileMd5Queue;

    /**
     * 线程停止的标记
     */
    private boolean flag = true;


    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public MyFileWriter(Queue<MyFile> fileMd5Queue) {
        this.fileMd5Queue = fileMd5Queue;
    }

    @Override
    public void run() {
        while (flag) {
            writeFile();
        }
    }

    private void writeFile() {
        //取出一个元素
        MyFile file = fileMd5Queue.poll();
        if (file != null) {
            System.out.println("file=" + file.getFile().getAbsolutePath() + ",MD5=" + file.getMd5());
        }
    }
}
