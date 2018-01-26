package com.chenyu.sign;

/**
 * 产品类
 *
 * @author yu_chen
 * @create 2018-01-26 10:46
 **/
public class Movie {

    private boolean flag = true;

    /**
     * flag为true进行生产
     *
     * @param name
     */
    public synchronized void make(String name) {
        if (!flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //进行生产
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name);
        //生产完毕，通知消费者进行消费
        this.notifyAll();
        flag = false;
    }

    /**
     * flag为false进行消费
     *
     * @param name
     */
    public synchronized void play(String name) {
        if (flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //进行消费
        System.out.println(name);
        //消费完毕通知生产者生产
        this.notifyAll();
        flag = true;

    }


}
