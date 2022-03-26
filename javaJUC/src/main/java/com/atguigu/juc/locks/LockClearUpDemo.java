package com.atguigu.juc.locks;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/24 16:09
 */
public class LockClearUpDemo {
    // 1
    static Object objectLock = new Object();

    public void m1(){
        // 2
        Object objectLock = new Object();  // 锁消除
        /**
         * 2 这个锁没有用   每次都是新new的  --  这个就是锁消除
         */
        synchronized (objectLock){

            System.out.println("--- hello lock");
        }
    }
    public static void main(String[] args) {

    }
}
