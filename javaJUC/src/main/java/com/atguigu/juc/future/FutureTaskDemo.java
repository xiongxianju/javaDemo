package com.atguigu.juc.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<Integer> futureTask = new FutureTask<>(()->{
            System.out.println(Thread.currentThread().getName() +"\t"+"come in ");
            return 1024;
        });

        new Thread(futureTask,"t1").start();
        System.out.println(" 继续讲课");
        // 只要出现get 方法 都是阻塞 等待结果出现
        System.out.println(futureTask.get());
        // 设置等候时间   过时不候
        // System.out.println(futureTask.get(2L, TimeUnit.SECONDS));

        //不要阻塞   尽量采用轮询方式
        while (true){
            if(futureTask.isDone()){
                System.out.println("结果 "+futureTask.get());
            }else{
                System.out.println("还在计算中 ");
            }
        }





    }
}
