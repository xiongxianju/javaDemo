package com.atguigu.juc.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * countdownLatch 使用方式场景
 *
 * @Author: xiongxianju
 * @Date: 2022/3/17 14:38
 */
public class AtomicIntegerDemo {

    public static final int size =50;

    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();
        CountDownLatch countDownLatch = new CountDownLatch(size) ;
        for(int i =0; i < size ; i++){
            new Thread(()->{
                try {
                    for (int j = 1; j <= 1000; j++) {
                        myNumber.addPlus();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            },String.valueOf(i)).start();
        }
        System.out.println(Thread.currentThread().getName()
        +" -- result " + myNumber.atomicInteger);

       /* try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()
                +" --111 result " + myNumber.atomicInteger);
    }
}

class MyNumber{
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addPlus(){
        atomicInteger.incrementAndGet();
    }
}
