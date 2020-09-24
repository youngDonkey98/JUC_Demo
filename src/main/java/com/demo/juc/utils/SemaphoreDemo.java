package com.demo.juc.utils;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *  JUC 的 辅助工具类之一 ： Semaphore
 *
 *  1.  acquire (获取) 当一个行程 调用 acquire 操作时 它要通过成功获取
 *      信号量 （ 信号量减 1 ）
 *
 *  2.  release (释放) 实际上会将信号量的值加 1 然后唤醒等待的线程
 *
 *  3.  信号量 主要用于两个目的 一个是用于多个资源的互斥使用 另一个用于并发线程数
 *      的控制
 */
public class SemaphoreDemo {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(3);//模拟3个停车位
        //模拟6部汽车
        for (int i = 1; i <=6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t 抢到了车位");
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    System.out.println(Thread.currentThread().getName()+"\t------- 离开");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }



}
