package com.demo.juc.utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 *  JUC 的 辅助工具类之一 ： CyclicBarrier
 *
 *      1. Cyclic(循环)  Barrier(屏障)  只做一件事
 *          让一组线程 达到 一个屏障（也可以叫做 同步点 ）时 被阻塞
 *
 *      2.  直到最后一个线程到达屏障时  屏障才会开门  所有被屏障拦截的线程才会继续干活
 *
 *      3.  线程 进入 屏障 通过 CyclicBarrier 的 await() 方法
 *
 */
public class CyclicBarrierDemo {

    private static final int NUMBER = 7;

       public static void main(String[] args) {

        //CyclicBarrier(int parties, Runnable barrierAction)
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER, ()->{System.out.println("*****集齐7颗龙珠就可以召唤神龙");}) ;

        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+"\t 星龙珠被收集 ");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }, String.valueOf(i)).start();
        }
    }

}
