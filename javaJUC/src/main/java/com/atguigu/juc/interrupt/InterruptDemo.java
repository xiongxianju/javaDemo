package com.atguigu.juc.interrupt;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author xxj98
 */
public class InterruptDemo {
    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
        Thread t1 =  new Thread(() ->{
            while(true){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Interrupted  == true 程序结束" );
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // sleep 会抛出异常   中断标准会被清除    这里需要重新设置中断状态
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                   // break;
                }
                System.out.println("hello Interrupted");
            }
        },"t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->{
            //设置线程中断标志位 为true
            t1.interrupt();
        },"t2").start();
    }

    /**
     * interrupt 中断位置被设置以后 并不是立刻stop线程  只是协商设置中断标识
     * @param args
     */
    public static void m4(String[] args) {
        //   interrupt 中断位置被设置以后 并不是立刻stop线程
        Thread t1 = new Thread(() ->{
            for(int i =1 ; i<300 ; i++) {
                System.out.println("-------    "+i);
            }
            System.out.println("t1线程中断的标识位置3 interrupt  " + Thread.currentThread().isInterrupted());
        },"t1");
        t1.start();
        System.out.println("t1线程中断的标识位置1 interrupt  " + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
        System.out.println("t1线程中断的标识位置2 interrupt  " + t1.isInterrupted());
    }

    /**
     * interrupt 中断
     * @param args
     */
    public static void m3(String[] args) {
        Thread t1 =  new Thread(() ->{
            while(true){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Interrupted  == true 程序结束" );
                    break;
                }
                System.out.println("hello Interrupted");
            }
        },"t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->{
            //设置线程中断标志位 为true
            t1.interrupt();
        },"t2").start();
    }

    public static void m2(String[] args) {
        new Thread(() ->{
            while(true){
                if(atomicBoolean.get()){
                    System.out.println("atomicBoolean  == true 程序结束" );
                }
                System.out.println("hello atomicBoolean");
            }
        },"t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->{
            atomicBoolean.set(true);
        },"t2").start();

    }

    public static void m1(String[] args) {
        new Thread(() ->{
            while(true){
                if(isStop){
                    System.out.println("isStop  == true 程序结束" );
                }
                System.out.println("hello isStop");
            }
        },"t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->{
           isStop = true;
        },"t2").start();

    }


}
