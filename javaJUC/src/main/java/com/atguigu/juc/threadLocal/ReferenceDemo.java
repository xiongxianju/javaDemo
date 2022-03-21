package com.atguigu.juc.threadLocal;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/19 22:36
 */
class MyObject{
    /**
     * 一般这个方法不用  这里为了讲解gc 做演示
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println("----- gc,finalize invoke");
      //  super.finalize();
    }
}
public class ReferenceDemo {


    public static void main(String[] args) {
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference =
                new PhantomReference<>(new MyObject() , referenceQueue);

        System.out.println("gc  before 够用"+phantomReference.get());

    }
    /**
     * 弱引用    gc了就会被回收
     * @param args
     */
    public static void weakReferenceDemo(String[] args) {
        WeakReference<MyObject> weakReference =
                new WeakReference<>(new MyObject());
        System.out.println("gc  before 够用"+weakReference.get());
        System.gc(); //开启gc
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gc  after够用 "+weakReference.get());
    }



    /**
     * 软引用 softReference
     * 当系统内存充足时   不会被回收
     * 当内存不足时   会被回收
     * @param args
     */
    public static void SoftReference(String[] args) {
        SoftReference<MyObject> softReference =
                new SoftReference<>(new MyObject());

  /*      System.out.println("gc  before 够用"+softReference);
        System.gc(); //开启gc
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gc  after够用 "+softReference);
  */
        // 设置参数  -Xms10m  -Xmx10m
        System.out.println("gc  before 够用"+softReference);

        System.gc();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gc  after 够用"+softReference);

        try{
            byte[]  bytes = new byte[9 *1024 * 1024];
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("gc  after 内存不够用"+softReference);
        }

    }


    /**
     * 强引用
     * @param args
     */
    public static void strongDemo(String[] args) {
        //默认  强引用  死了都不放手
        MyObject myObject = new MyObject();
        System.out.println("gc  before"+myObject);
        //
        myObject = null;
        System.gc(); //开启gc

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("gc  after "+myObject);
    }

}
