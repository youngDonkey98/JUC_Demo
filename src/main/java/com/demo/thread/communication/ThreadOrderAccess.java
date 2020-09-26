package com.demo.thread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  定制化线程间通讯
 *      线程A 循环五次 调用B
 *      线程B 循环十次 调用C
 *      线程C 循环十五次 调用A
 */


// 资源类
class ShareResource {
    /**
     *  思路 ：
     *      0. 声明一个锁 三把钥匙
     *      1. 声明一个标识位 flag
     *      2. 判断标识位 flag = 1  A ， flag = 2 B ， flag = 3 C
     *      3. 干活
     *      4. 通知 变更 flag ， 唤醒下一个线程
     */

    // 旗子
    private int flag = 1;

    // 锁
    private final Lock lock = new ReentrantLock();

    // 三把钥匙
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();


    // A 消费者
    public void print5() throws InterruptedException {
        lock.lock();
        try {
            if (flag != 1) {
                condition1.await();
            }
            // 干活
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 旗子加一
            flag = 2;
            // 唤醒下一个线程
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


    // B 消费者
    public void print10() throws InterruptedException {
        lock.lock();
        try {
            if (flag != 2) {
                condition2.await();
            }
            // 干活
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 旗子加一
            flag = 3;
            // 唤醒下一个线程
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


    // C 消费者
    public void print15() throws InterruptedException {
        lock.lock();
        try {
            if (flag != 3) {
                condition3.await();
            }
            // 干活
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 旗子加一
            flag = 1;
            // 唤醒下一个线程
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


}


public class ThreadOrderAccess {

    public static void main(String[] args) {

        // 线程操作资源类：
        ShareResource shareResource = new ShareResource();

        // 创建线程
        new Thread(()->{
            // 循环
            for (int i = 1; i <10 ; i++) {
                try {
                    shareResource.print5();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();
        // 创建线程
        new Thread(()->{
            // 循环
            for (int i = 1; i <10 ; i++) {
                try {
                    shareResource.print10();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();
        // 创建线程
        new Thread(()->{
            // 循环
            for (int i = 1; i <10 ; i++) {
                try {
                    shareResource.print15();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"CC").start();

    }
}
