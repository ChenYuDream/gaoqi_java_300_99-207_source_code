package com.chenyu;

import java.util.concurrent.*;

/**
 * @author yu_chen
 * @create 2018-01-25 17:37
 **/
public class Call {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Integer> submit = executorService.submit(new Race());
        System.out.println(submit.get());

    }
}

class Race implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        //具体的run方法
        return 10;
    }
}