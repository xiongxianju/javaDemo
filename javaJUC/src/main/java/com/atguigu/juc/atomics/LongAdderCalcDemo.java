package com.atguigu.juc.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/17 21:42
 */

class ClickNumber{
    int number = 0;
    public synchronized  void addSync(){
        number ++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void add_atomicInteger(){
        atomicInteger.incrementAndGet();
    }

    AtomicLong  atomicLong = new AtomicLong();
    public void add_atomicLong(){
        atomicLong.incrementAndGet();
    }

    LongAdder longAdder = new LongAdder();
    public void add_longAdder(){
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator((x,y) -> x+y,0);
    public void add_longAccumulator(){
        longAccumulator.accumulate(1);
    }


}
public class LongAdderCalcDemo {

    private static final int size =50;
    private static final int num =10000;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        long startTime ;
        long endTime ;
        CountDownLatch countDownLatch1 = new CountDownLatch(size);
        CountDownLatch countDownLatch2 = new CountDownLatch(size);
        CountDownLatch countDownLatch3 = new CountDownLatch(size);
        CountDownLatch countDownLatch4 = new CountDownLatch(size);
        CountDownLatch countDownLatch5 = new CountDownLatch(size);

        startTime = System.currentTimeMillis();
        for(int i =0 ;i<size ;i++){
            new Thread( () ->{
                try{
                    for(int j =0; j < 100 * num ;j++){
                        clickNumber.addSync();
                    }
                }finally {
                    countDownLatch1.countDown();
                }

            },String.valueOf(i)).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println(" ---- cosTime:"
                + ( endTime - startTime) +" 毫秒 Sync    " + clickNumber.number);


        startTime = System.currentTimeMillis();
        for(int i =0 ;i<size ;i++){
            new Thread( () ->{
                try{
                    for(int j =0; j < 100 * num ;j++){
                        clickNumber.add_atomicInteger();
                    }
                }finally {
                    countDownLatch2.countDown();
                }

            },String.valueOf(i)).start();
        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        System.out.println(" ---- cosTime:"
                + ( endTime - startTime) +" 毫秒 atomicInteger   " + clickNumber.atomicInteger);


        startTime = System.currentTimeMillis();
        for(int i =0 ;i<size ;i++){
            new Thread( () ->{
                try{
                    for(int j =0; j < 100 * num ;j++){
                        clickNumber.add_atomicLong();
                    }
                }finally {
                    countDownLatch3.countDown();
                }

            },String.valueOf(i)).start();
        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        System.out.println(" ---- cosTime:"
                + ( endTime - startTime) +" 毫秒 atomicLong  " + clickNumber.atomicLong);




        startTime = System.currentTimeMillis();
        for(int i =0 ;i<size ;i++){
            new Thread( () ->{
                try{
                    for(int j =0; j < 100 * num ;j++){
                        clickNumber.add_longAdder();
                    }
                }finally {
                    countDownLatch4.countDown();
                }

            },String.valueOf(i)).start();
        }
        countDownLatch4.await();
        endTime = System.currentTimeMillis();
        System.out.println(" ---- cosTime:"
                + ( endTime - startTime) +" 毫秒  longAdder   " + clickNumber.longAdder);


        startTime = System.currentTimeMillis();
        for(int i =0 ;i<size ;i++){
            new Thread( () ->{
                try{
                    for(int j =0; j < 100 * num ;j++){
                        clickNumber.add_longAccumulator();
                    }
                }finally {
                    countDownLatch5.countDown();
                }

            },String.valueOf(i)).start();
        }
        countDownLatch5.await();
        endTime = System.currentTimeMillis();
        System.out.println(" ---- cosTime:"
                + ( endTime - startTime) +" 毫秒 longAccumulator   " + clickNumber.longAccumulator);
    }
}
