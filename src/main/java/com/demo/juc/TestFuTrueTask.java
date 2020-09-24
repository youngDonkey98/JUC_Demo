package com.demo.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class MyThread2 implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "MyThread2 implements Callable ";
    }
}
/**
 *   // Thread <--- Runnable <--- FutureTask(Callable)
 *
 *   创建线程的第三种方法 实现 Callable 接口
 *
 */

public class TestFuTrueTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<String> futureTask = new FutureTask<String>(new MyThread2());
        new Thread(futureTask,"AA").start();

        // 获取到结果 调用get()
        System.out.println("futureTask.get() = " + futureTask.get());

    }
}
