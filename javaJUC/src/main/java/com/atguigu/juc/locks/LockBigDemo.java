package com.atguigu.juc.locks;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/24 16:15
 */
public class LockBigDemo {
    static Object objectLock = new Object();

    public static void main(String[] args) {
        new Thread(() ->{
            synchronized (objectLock){
                System.out.println(" -----1 ");
            }
            synchronized (objectLock){
                System.out.println(" -----2 ");
            }
            synchronized (objectLock){
                System.out.println(" -----3 ");
            }
            synchronized (objectLock){
                System.out.println(" -----4 ");
            }
            /**
             * 锁会被优化改成下面
             */
            synchronized (objectLock){
                System.out.println(" -----1 ");

                System.out.println(" -----2 ");

                System.out.println(" -----3 ");

                System.out.println(" -----4 ");
            }


        }).start();
    }
}
