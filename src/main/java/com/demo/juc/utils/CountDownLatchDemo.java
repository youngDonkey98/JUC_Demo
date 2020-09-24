package com.demo.juc.utils;

import java.util.concurrent.CountDownLatch;

/**
 *  JUC 辅助工具类之一 ：CountDownLatch
 *
 *  1.  CountDownLatch 主要有两个方法 当一个 或 多个线程调用 await方法时 这些线程会阻塞
 *
 *  2.  其他线程调用 countDown 方法 会讲计数器 减一
 *      调用countDown方法的线程不会阻塞
 *
 *  3.  当计数器的值变为 0 时 因为 await 方法阻塞的线程会被唤醒 继续执行
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        // 6个同学陆续离开教室后值班同学才可以关门。

        // CountDownLatch 构造器中的数 需要和循环次数一致 否则会一直等待
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t 号同学离开教室");
            countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t****** 班长关门走人，main线程是班长");


    }
}
