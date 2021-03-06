package com.chenyu.sign;

/**
 * 消费者
 *
 * @author yu_chen
 * @create 2018-01-26 10:48
 **/
public class Player implements Runnable {

    private Movie movie;

    public Player(Movie movie) {
        super();
        this.movie = movie;
    }

    @Override
    public void run() {
        test1();
    }

    public void test1() {
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                movie.play("消费：左青龙");
            } else {
                movie.play("消费：右白虎");
            }
        }
    }
}
