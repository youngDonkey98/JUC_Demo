package com.demo.block.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *  BlockingQueue ： 阻塞队列
 *      当队列 是空的 从队列中获取元素 将被阻塞
 *      当队列 是满的 向队列中添加元素 将被阻塞
 *
 *    阻塞队列的用途 ：
 *      在多线程领域 ： 阻塞 在某些情况下 会 挂起线程（即阻塞） 一旦条件满足
 *                      被挂起的线程 又会 自动 被唤起
 *      好处 ：
 *          不需要关系 什么时候 需要 阻塞线程 什么时候 唤醒线程（BlockingQueue一手包办0）
 *
 *    ArrayBlockingQueue：
 *      由数组结构组成的有界阻塞队列
 *
 *    LinkedBlockingQueue：
 *      由链表结构组成的有界（但大小默认值为integer.MAX_VALUE）阻塞队列
 *
 *    SynchronousQueue：
 *      不存储元素的阻塞队列，也即单个元素的队列
 *
 *    PriorityBlockingQueue：
 *      支持优先级排序的无界阻塞队列
 *
 *    DelayQueue：
 *      使用优先级队列实现的延迟无界阻塞队列
 *
 *    LinkedTransferQueue：
 *      由链表组成的无界阻塞队列
 *
 *    LinkedBlockingDeque：
 *      由链表组成的双向阻塞队列
 */


public class BlockingQueueDemo {
    public static void main(String[] args) throws Exception{
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
//        //第一组
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        System.out.println(blockingQueue.element());

//        System.out.println(blockingQueue.add("x"));
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//    第二组
//        System.out.println(blockingQueue.offer("a"));
//        System.out.println(blockingQueue.offer("b"));
//        System.out.println(blockingQueue.offer("c"));
//        System.out.println(blockingQueue.offer("x"));
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//    第三组
//         blockingQueue.put("a");
//         blockingQueue.put("b");
//         blockingQueue.put("c");
//         blockingQueue.put("x");
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());

//    第四组
//        System.out.println(blockingQueue.offer("a"));
//        System.out.println(blockingQueue.offer("b"));
//        System.out.println(blockingQueue.offer("c"));
//        System.out.println(blockingQueue.offer("a",3L, TimeUnit.SECONDS));
    }
}