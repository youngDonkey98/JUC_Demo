package com.demo.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 *  合并分支框架
 *
 *      Fork：把一个复杂任务进行分拆，大事化小
 *      Join：把分拆任务的结果进行合并
 *
 *  相关类 ：
 *      ForkJoinPool ： 分之合并池 （ 类比于线程池 ）
 *      ForkJoinTask ： 未来任务 （ 类比于 FutureTask ）
 *      RecursiveTask ： ForkJoinTask 的 子类  继承后可以实现 递归调用 的 任务
 *
 */



class MyTask extends RecursiveTask<Integer> {

    public MyTask(int begin,int end){
        this.begin = begin;
        this.end = end;
    }

    // 第一个 ： 声明一个变量 这个变量 记录 两个数之间的差值
    //   没有大于 10 采用一种算法 大于 10 采用 另一种算法
    private int VALUE = 10;

    private int begin;
    private int end;
    private int result;

    // 用来 计算 递归操作 的 方法
    @Override
    protected Integer compute() {

        // 判断 没有大于 10 的
        if (end - begin <= VALUE) {
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        } else {
            // 大于 10
            // 获取 一个中间值
            int mid = (begin + end) / 2;
            MyTask myTask1 = new MyTask(begin, mid);
            MyTask myTask2 = new MyTask(mid+1, end);
            // 计算
            myTask1.fork();
            myTask2.fork();

            result += myTask1.join() + myTask2.join();
        }
        return result;
    }
}



public class ForkJoinDemo {

    public static void main(String[] args) {
        MyTask myTask = new MyTask(0, 100);
        // 创建 ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 调用池中的方法
        // forkJoinPool.submit(ForkJoinTask<T> task);
        // MyTask extends RecursiveTask<Integer> extends ForkJoinTask<V>
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
        // 使用 get() 获得未来任务 得到的结果
        try {
            System.out.println(forkJoinTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 关闭连接池
        forkJoinPool.shutdown();
    }

}
