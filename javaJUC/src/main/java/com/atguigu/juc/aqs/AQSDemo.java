package com.atguigu.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/27 8:43
 */
public class AQSDemo {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        new Thread(() ->{
            lock.lock();
            try {
                System.out.println(" ---- come in A");
                TimeUnit.MILLISECONDS.sleep(5);
            }catch (Exception e){

            }finally {
                lock.unlock();
            }

        },"A").start();
    }
}
