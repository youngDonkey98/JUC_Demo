package com.demo.lock;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  锁的类型
 *      悲观锁：
 *          它认为 它在操作数据的时候 其他人都会改动数据 所有直接加锁
 *              关系型数据库：mysql oracle
 *      乐观锁：
 *          它认为 它在操作数据的时候 其他人都不会改动数据 只有在提交的时候 （通过版本号来 更新数据的版本）
 *              非关系型数据库：redis
 *
 *      行锁：
 *          范围小，容易发生死锁
 *
 *      表锁：
 *          范围大
 *
 *      读锁： 共享锁
 *
 *      写锁： 对象锁，也叫 排他锁
 *
 *          读写锁 都会发生死锁
 */

/**
 *  读写锁 ： ReentrantReadWriteLock    reentrant  adj. 再进去的；凹角的 n. 凹角；再进入
 *
 *      等线程写完之后，才能另一个线程写入数据
 *
 *      ReentrantReadWriteLock 类里面 有 两个静态内部类
 *          public static class ReadLock implements Lock, java.io.Serializable
 *          public static class WriteLock implements Lock, java.io.Serializable
 */


// 缓存。。。。资源类
class MyCache{

    // 声明一个map
    private HashMap map = new HashMap();
    // 声明一个读写锁对象
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    // 声明写入方法
    public void set(String key,String value){
        // 上锁
        rwl.writeLock().lock();
        try {
            // 提示正在写入数据
            System.out.println(Thread.currentThread().getName()+"\t 正在写"+key);
            // 稍微睡一会
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 调用map 方法
            map.put(key,value);
            // 提示用户已经写入完成
            System.out.println(Thread.currentThread().getName()+"\t 写完了"+key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.writeLock().unlock();
        }
    }

    // 声明读方法
    public Object get(String key){
        // 声明对象
        Object object = null;
        // 上锁
        rwl.readLock().lock();
        try {
            // 提示谁谁获取数据
            System.out.println(Thread.currentThread().getName()+"\t 正在读"+key);
            // 获取map 数据
            object = map.get(key);
            // 提示读取数据完成
            System.out.println(Thread.currentThread().getName()+"\t 读完了"+object);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.readLock().unlock();
        }
        // 返回数据
        return object;
    }

}


public class ReadWriteLockDemo {

    public static void main(String[] args) {
        // 声明一个map 集合，在map 集合中 添加数据，获取数据。
        // 使用多线程的方式来调用方法。
        // 线程操作资源类
        MyCache myCache = new MyCache();

        // 创建线程 调用写数据方法
        for (int i = 1; i < 5; i++) {
            // 将i 复制给 num
            int num = i;
            new Thread(()->{
                // 放入的数据key，value 都是num
                myCache.set(num+"",num+"");
            },String.valueOf(i)).start();
        }
        // 创建线程 调用读数据方法
        for (int i = 1; i < 5; i++) {
            // 将i 复制给 num
            int num = i;
            new Thread(()->{
                // 放入的数据key，value 都是num
                myCache.get(num+"");
            },String.valueOf(i)).start();
        }

        // 总结： 等线程写完之后，才能另一个线程写入数据！
    }
}
