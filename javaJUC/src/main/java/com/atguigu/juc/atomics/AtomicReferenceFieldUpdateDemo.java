package com.atguigu.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/17 17:30
 */
class MyVar{
    public volatile Boolean isInit = Boolean.FALSE;
    AtomicReferenceFieldUpdater<MyVar ,Boolean> fieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(MyVar.class,Boolean.class,"isInit");

    public void init(MyVar myVar){
        if(fieldUpdater.compareAndSet(myVar , Boolean.FALSE , Boolean.TRUE)){
            System.out.println(Thread.currentThread().getName() + "start init");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "end init");
        }else {
            System.out.println(Thread.currentThread().getName() + "抢占失败  已经有线程在修改 ");
        }
    }
}

/**
 * 多线程并发调用一个类的初始化方法 ，如果未被初始化，将执行初始化工作，要求只能初始化一次
 */
public class AtomicReferenceFieldUpdateDemo {

    public static void main(String[] args) {
        MyVar myVar = new MyVar();
        for(int i =0 ;i < 5 ;i++){
            new Thread(() ->{
                myVar.init(myVar);
            },i +" ---").start();
        }

    }
}
