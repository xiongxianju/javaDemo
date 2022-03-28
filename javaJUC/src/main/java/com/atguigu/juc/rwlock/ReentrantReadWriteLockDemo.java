package com.atguigu.juc.rwlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/27 10:14
 */
class MyResource{
    Map<String ,String > map = new HashMap<>();
    // == reentrantLock === synchronized
    Lock lock = new ReentrantLock();

    // ReentrantReadWriteLock 读写锁  一体二面   读写互斥  读读共享
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void write(String key ,String value){
  //     lock.lock();
        readWriteLock.writeLock().lock();

        try{
            System.out.println(Thread.currentThread().getName() + "---- 正在写入");
            map.put(key, value);
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println(Thread.currentThread().getName() + "---- 完成写入");
        }catch (Exception e){

        } finally{
           // lock.unlock();
            readWriteLock.writeLock().unlock();
        }
    }

    public void read(String key){
      //  lock.lock();
        readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "---- 正在读取");
            String value =  map.get(key);
            System.out.println(Thread.currentThread().getName() + "---- 完成读取" + value);
        }finally {
            readWriteLock.readLock().unlock();
        }
    }
}

public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        MyResource myResource = new MyResource();
        for(int i = 0 ;i < 10 ;i++){
            int finali = i;
            new Thread(()->{
                myResource.write(finali+"" ,finali+"");

            },String.valueOf(i)).start();
        }

        for(int i = 0 ;i < 10 ;i++){
            int finali = i;
            new Thread(()->{
                myResource.read(finali+"");

            },String.valueOf(i)).start();
        }
    }
}
