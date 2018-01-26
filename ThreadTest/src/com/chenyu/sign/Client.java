package com.chenyu.sign;

/**
 * 客户端调用类
 *
 * @author yu_chen
 * @create 2018-01-26 10:53
 **/
public class Client {

    public static void main(String[] args) throws InterruptedException {
        Movie movie = new Movie();

        Player p = new Player(movie);
        Maker m = new Maker(movie);

        Thread thread1 = new Thread(p);
        Thread thread2 = new Thread(m);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("线程执行完毕");
        /*while (true) {
            if (!(thread1.isAlive() || thread2.isAlive())) {
                System.out.println("线程执行完毕");
                break;
            }
        }*/
    }
}
