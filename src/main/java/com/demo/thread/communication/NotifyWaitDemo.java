package com.demo.thread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  线程通讯
 *
 *  如果不加锁 ：IllegalMonitorStateException
 *
 *  如果有多套 生产者 消费者 则 会存在 "虚假唤醒" 的 情况
 *
 *  使用condition时 "虚假唤醒" 是允许发生的 需要将 判断 变为 循环 中
 *  （文档中 建议 程序员 总是认为 虚假唤醒 可以发生 所有总是在等待一个回路）
 *
 */


class ShareDataOne {
    // 共享资源类
    // 定义一个变量
    private int number = 0;

    // 使用lock锁 代替 synchronized
    private final Lock lock = new ReentrantLock();

    // wait() - await();
    private Condition condition = lock.newCondition();

    // 生产者线程
    //public synchronized void increment() throws InterruptedException {
    public void increment() throws InterruptedException {
        // 判断
        lock.lock();
        //if (number != 0) {
        try {
            while (number != 0) {
                //this.wait();
                condition.await();
            }
            // 干活
            ++number;
            System.out.println(Thread.currentThread().getName() +"\t" + number);
            // 通知/唤醒
            //this.notifyAll();
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // 消费者线程
    public synchronized void decrement() throws InterruptedException {
        // 判断
        lock.lock();
        try {
            while (number == 0) {
               // this.wait();
                condition.await();
            }
            // 干活
            --number;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            // 通知
            //this.notifyAll();
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}



public class NotifyWaitDemo {

    public static void main(String[] args) {
        // 线程资源操作类
        ShareDataOne shareDataOne = new ShareDataOne();

        // 创建多个线程
        // 线程一：+
        new Thread(()->{
            // 生产
            for (int i = 0; i < 10; i++) {
                try {
                    shareDataOne.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA+").start();

        // 线程二：-
        new Thread(()->{
            // 生产
            for (int i = 0; i < 10; i++) {
                try {
                    shareDataOne.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB-").start();

        // 当有多套生产者 消费者时 会存在 "虚假唤醒" 的 情况

        // 线程三：+
        new Thread(()->{
            // 生产
            for (int i = 0; i < 10; i++) {
                try {
                    shareDataOne.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"CC+").start();

        // 线程四：-
        new Thread(()->{
            // 生产
            for (int i = 0; i < 10; i++) {
                try {
                    shareDataOne.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"DD-").start();
    }
}
