package com.demo.async;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 *   异步编排器
 *         CompletableFuture 类
 *          runAsync()      // 无参数 无返回值
 *          supplyAsync()   // 无参数 有返回值
 *          whenComplete()  // 有参数 无返回值
 *          exceptionally() // 有参数 有返回值
 *          get()
 *
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("哈哈");
        });
        System.out.println(completableFuture.get());

        // 写代码！ 创建一个
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "======================");
           // int i = 1/0;
            return 1024;
        }).whenComplete((t,u)->{
            // t : 上面 return 的值
            System.out.println(t+"---------------");
            // u : 上面抛出的异常
            System.out.println(u+"---------------");
        }).exceptionally((t)->{
            System.out.println(t.getMessage());
            return 404;
        });
        // 如果出异常 此 get() 为 404
        System.out.println(integerCompletableFuture.get());
    }
}
