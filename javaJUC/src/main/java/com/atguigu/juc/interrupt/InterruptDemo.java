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
