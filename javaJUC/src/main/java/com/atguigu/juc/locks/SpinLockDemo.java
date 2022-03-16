package com.atguigu.juc.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/16 22:44
 */
public class SpinLockDemo {

    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        System.out.println(Thread.currentThread().getName() +"\t" + "----- come in");
        while(!atomicReference.compareAndSet(null,Thread.currentThread())){

        }
        System.out.println(Thread.currentThread().getName() +"\t" + "----- 获取锁");
    }

    public void myUnLock(){
        atomicReference.compareAndSet(Thread.currentThread() ,null);
        System.out.println(Thread.currentThread().getName() +"\t" + "----- 释放锁成功");
    }
    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread( () ->{
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        },"t1").start();


        new Thread( () ->{
            spinLockDemo.myLock();
            spinLockDemo.myUnLock();
        },"t2").start();
    }
}
