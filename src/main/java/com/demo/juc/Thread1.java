package com.demo.juc;

/**
 *  线程的四种创建方法
 *      1. new Thread()
 *      2. 实现 Runnable
 *      3. 实现 Callable
 *      4. 线程池
 */

//新建类实现runnable接口
class MyThread implements Runnable{

    @Override
    public void run() {

    }
}

public class Thread1 extends Thread{


    public static void main(String[] args) {
        // 方法一：
        Thread thread = new Thread();

    }
}
