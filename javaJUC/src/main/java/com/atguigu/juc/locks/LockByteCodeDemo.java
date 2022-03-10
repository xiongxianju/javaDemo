package com.atguigu.juc.locks;

/**
 * @author xxj98
 */
public class LockByteCodeDemo {
    final  Object object = new Object();
    public void m1(){
          /*  synchronized(object){
                System.out.println(" -----   hello sync ");
                throw new NullPointerException("1111");
            }*/
    }


    public synchronized void m2(){
        System.out.println(" -----   hello sync ");
    }

    public static void main(String[] args) {

    }
}
