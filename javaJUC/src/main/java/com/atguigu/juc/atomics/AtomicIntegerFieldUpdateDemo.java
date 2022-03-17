package com.atguigu.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/17 16:48
 */


class  BankAccount{
    String bankName ="ccb";

    //以一种线程安全的方式操作非线程安全对象内的某些字段

    // 1 更新对象属性必须使用 public volatile 修饰符
    public volatile int money = 0;
 // 2  因为对象的属性修改类型原子类都是抽象类，所以每次都必须
    //使用静态方法newUpdate()创建一个更新器 并且需要设置想要更新的类和属性
    AtomicIntegerFieldUpdater fieldUpdater =
         AtomicIntegerFieldUpdater.newUpdater(BankAccount.class,"money");


    AtomicInteger atomicInteger =
            new AtomicInteger();

    public void transfer(BankAccount bankAccount){
        fieldUpdater.incrementAndGet(bankAccount);
        atomicInteger.incrementAndGet();
    }
}
public class AtomicIntegerFieldUpdateDemo {

    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
        for(int i =0; i< 1000; i++){
            new Thread(()->{
                bankAccount.transfer(bankAccount);
            }).start();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(bankAccount.money);

        System.out.println(bankAccount.atomicInteger);
    }
}