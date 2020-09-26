package com.demo.threadpool;


import java.util.concurrent.*;

/**
 *  线程池：
 *      线程池的优势：
 *          线程池做的工作只要是控制运行的线程数量 处理过程中将任务放入队列 然后在线程创建后启动这些任务
 *          如果线程数量超过了最大数量 超出数量的线程排队等候 等其他线程执行完毕 再从队列中取出任务来执行
 *
 *  主要特点：
 *      线程复用
 *      控制最大并发数
 *      管理线程
 *
 *   第一：降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的销耗。
 *
 *   第二：提高响应速度。当任务到达时，任务可以不需要等待线程创建就能立即执行。
 *
 *   第三：提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会销耗系统资源，还会降低系统的稳定性，
 *          使用线程池可以进行统一的分配，调优和监控。
 *
 *   线程池的创建 ： Executors ： 线程池工具类
 *      1. Executors.newFixedThreadPool(int n) ：
 *          底层 ：  return new ThreadPoolExecutor()
 *          执行长期任务性能好，创建一个线程池，一池有N个固定的线程，有固定线程数的线程
 *
 *      2. Executors.newSingleThreadExecutor()
 *          底层 ：  return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor())
 *          一个任务一个任务的执行，一池一线程
 *
 *      3. Executors.newCachedThreadPool()
 *          底层 ：  return new ThreadPoolExecutor()
 *          执行很多短期异步任务，线程池根据需要创建新线程，但在先前构建的线程可用时将重用它们。
 *          可扩容，遇强则强
 *
 *
 *    ThreadPoolExecutor 类 最原始的 线程池 构造器 （里面为 最原始的 线程池 核心参数）
 *      public ThreadPoolExecutor(int corePoolSize,     // 核心线程数
 *                                      线程池中的常驻核心线程数
 *
 *                               int maximumPoolSize,   // 最大线程数
 *                                      线程池中 能够容纳同时执行的 最大线程数 此数值 必须大于等于1
 *
 *                               long keepAliveTime,    // 空闲线程存活时间
 *                                      多余的空闲线程的存活时间 当前池中线程数量超过corePoolSize时
 *                                      当空闲时间 达到keepAliveTime时 多余线程会被销毁直到
 *                                      只剩下corePoolSize个线程为止
 *
 *                               TimeUnit unit,         // 存活时间的单位
 *                                      keepAliveTime的单位
 *
 *                               BlockingQueue<Runnable> workQueue, // 阻塞队列
 *                                      任务队列 被提交但尚未被执行的任务
 *
 *                               ThreadFactory threadFactory,       // 线程工厂
 *                                      表示生成线程池中工作线程的线程工厂 用于创建线程 一般默认的即可
 *
 *                               RejectedExecutionHandler handler   // 拒绝策略
 *                                      拒绝策略 表示当队列满了 并且工作线程大于
 *                                      等于线程池的最大线程数（maximumPoolSize）时如何来拒绝 请求执行的runnable的策略
 *
 *
 *  线程池 工作原理
 *      例子 ： 最大线程池数为 5
 *              核心线程数为 2
 *              阻塞队列 容量 3
 *      1. 创建线程池后 线程池中的线程为 0
 *      2. 进来第一个任务 调用execute()方法 直接到使用核心线程
 *      3. 第二个任务进来 同 2
 *      4. 第三个任务进来 先去看核心线程池中是否有空的 没有 进入阻塞队列
 *      5. 第四个 第五个 任务 同 4
 *      6. 第六个任务进来 发现 核心线程 被占用 队列也满了 将会去使用最大线程池中的 另外三个非核心线程
 *      7. 第七个 第八个 任务 同 5
 *      8. 第九个任务进来 发现 连 非核心线程 都被使用完了 执行线程池的拒绝策略
 *      9. 后面的任务同 8
 *      10. 如果核心线程池中的任务处理完了 队列的也处理完了 非核心的线程也处理完了
 *          非核心的线程在配置的存活时间内没有任务 非核心线程将会 挂掉 始终保持核心线程池的线程数
 *
 *
 *  拒绝策略 ：
 *      jdk ：
 *          AbortPolicy(默认)：直接抛出RejectedExecutionException异常阻止系统正常运行
 *
 *          CallerRunsPolicy："调用者运行"( 下面例子为 main ) 一种调节机制 该策略既不会抛弃任务 也不会抛出异常
 *                          而是将某些任务回退到调用者 从而降低新任务的流量
 *
 *          DiscardOldestPolicy：抛弃队列中等待最久的任务 然后把当前任务加人队列中尝试再次提交当前任务
 *                              ( 咱也不知道 谁是等待最久的 )
 *
 *          DiscardPolicy：该策略默默地丢弃无法处理的任务 不予任何处理也不抛出异常 如果允许任务丢失 这是最好的一种策略
 *                          ( 和第三个相似 但是这个直接抛弃最后面的 )
 *
 *
 */
public class MyThreadPoolDemo {

    // 线程执行是由cpu 抢占机制决定的！
    public static void main(String[] args) {

//       ExecutorService threadPool =  Executors.newFixedThreadPool(5); //一个银行网点，5个受理业务的窗口
//       ExecutorService threadPool =  Executors.newSingleThreadExecutor(); //一个银行网点，1个受理业务的窗口
//       ExecutorService threadPool =  Executors.newCachedThreadPool(); //一个银行网点，可扩展受理业务的窗口

        // 固定数的线程池，一池五线程
        // 自定义线程池：new ThreadPoolExecutor
        // Executors 线程的工具类
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                3L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
                // new ThreadPoolExecutor.CallerRunsPolicy()
                // new ThreadPoolExecutor.DiscardOldestPolicy()
                // new ThreadPoolExecutor.DiscardPolicy()
        );

        //10个顾客请求 在银行办理业务！
        try {
            for (int i = 1; i <=10; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }

}
