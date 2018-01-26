package com.chenyu;

/**
 * @author yu_chen
 * @create 2018-01-25 17:27
 **/
public class Client {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("");
        });
        thread.start();
    }
}
