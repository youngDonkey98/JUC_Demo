package com.demo.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 *  FuTrueTask : 未来的任务 只做一件事 异步调用
 *
 *  1. 主线程中 执行比较耗时的操作时 但 又不想阻塞主线程时 可以把这些作业 交给Future对象在
 *      后台完成 当主线程将来需要时 就可以通过Future对象获得后台作业的计算结果 或者执行状态
 *     （ Future对象.get() ）
 *
 *  2. 仅在 计算完后时 才能检索结果 如果计算尚未完成 则阻塞 get 方法 一旦计算完成 就不会再重新
 *      开始 或 重新计算 get 方法而获取结果 只有在计算完成时获取  否则会一直阻塞 直到任务转入
 *      完成状态 然后会返回结果 或者 抛出异常
 */
public class TestFuTrueTask01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask1 = new FutureTask<Integer>(()->{
            System.out.println(Thread.currentThread().getName()+"  come in callable");
            TimeUnit.MILLISECONDS.sleep(1000);
            return 1024;
        });

        FutureTask<Integer> futureTask2 = new FutureTask<Integer>(()->{
            System.out.println(Thread.currentThread().getName()+"  come in callable");
            TimeUnit.MILLISECONDS.sleep(1000);
            return 1024*2;
        });

        new Thread(futureTask1,"AA").start();
        new Thread(futureTask2,"BB").start();

        // 在最后获取数据结果
        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());

    }

}
