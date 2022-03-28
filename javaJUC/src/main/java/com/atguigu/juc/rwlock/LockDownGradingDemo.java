package com.atguigu.juc.rwlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  * 锁降级：遵循获取写锁→再获取读锁→再释放写锁的次序，写锁能够降级成为读锁。
 *  *
 *  * 如果一个线程占有了写锁，在不释放写锁的情况下，它还能占有读锁，即写锁降级为读锁。
 *  悲观读  ----   读的时候不能进去写  只能读的全部结束才能写
 * @Author: xiongxianju
 * @Date: 2022/3/27 11:30
 */
public class LockDownGradingDemo {

    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        // 有且仅有一个线程  这个就是锁降级   ---》写锁可以降级为读锁   保证写后立即读

        writeLock.lock();
        System.out.println("------1111");
        // 这里没有解锁 后面执行读锁
      //  writeLock.unlock();

        readLock.lock();
        System.out.println("------read");
        readLock.unlock();
        writeLock.unlock();





      /*  // 这里没有解锁  写锁不可以降级为读锁
        //  writeLock.unlock();

        readLock.lock();
        System.out.println("------read");
        writeLock.lock();
        System.out.println("------1111");

        readLock.unlock();
        writeLock.unlock();*/
    }
}
