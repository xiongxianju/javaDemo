package com.atguigu.juc.interrupt;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xxj98
 */
public class LockSupportDemo {
    static  Object objectLock = new Object();
    static Lock lock = new ReentrantLock();

    static Condition condition = lock.newCondition();

    /**
     * LockSupport   park  permit 获取许可证  0,1
     * park 和 unPark 执行顺序没要求
     * unPark 只能管一次
     *
     * @param args
     */

    public static void main(String[] args) {

        Thread t1  =  new Thread(() ->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "    ---- come in");
            LockSupport.park();
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "    ---- 被唤醒");
        },"t1");
        t1.start();

         new Thread(() ->{
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "    ---- 通知");
        },"t2").start();

    }

    /**
     * await 和 signal  一定在Lock unLock 内容里面
     * signal 先等待在唤醒    线程才能被唤醒
     * @param args
     */
    public static void lockAwaitSignal(String[] args) {
        new Thread(() ->{
            lock.lock();
            try {

                System.out.println(Thread.currentThread().getName() + "    ---- come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "    ---- 被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }


        }, "t1").start();


        new Thread(() ->{
                lock.lock();
                try {
                    condition.signal();
                    System.out.println(Thread.currentThread().getName() + "    ---- 发送通知");
                }finally {
                    lock.unlock();
                }

        }, "t2").start();
    }

    /**
     * 1.  wait 和notify 一定是在synchronized内部 里面执行的
     * 2   notify 要在wait之后执行才会被唤醒线程
     * @param args
     */
    public static void syncWaitNotify(String[] args) {
        new Thread(() ->{
            synchronized (objectLock){
                System.out.println(Thread.currentThread().getName() + "    ---- come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "    ---- 被唤醒");
            }
        },"t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->{
            synchronized (objectLock){
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "    ---- 发出通知");
            }
        },"t2").start();
    }
}
