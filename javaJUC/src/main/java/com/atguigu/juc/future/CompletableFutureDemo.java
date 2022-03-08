package com.atguigu.juc.future;

import java.util.concurrent.*;

/**
 * @author xxj98
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,20,1, TimeUnit.SECONDS,new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture future3 =  CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName() + "----come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 11;
        }).thenApply(f ->{
            return f+2;
        }).whenComplete((v,e) ->{
            if(e == null){
                System.out.println("-----result " + v);
            }
        }).exceptionally(e ->{
            e.printStackTrace();
            return null;
        });

         // System.out.println(future3.get());

        System.out.println("-----main  over ");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        poolExecutor.shutdown();
    }



    public static void m1(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,20,1, TimeUnit.SECONDS,new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        CompletableFuture future = CompletableFuture.runAsync(()->{
            System.out.println(Thread.currentThread().getName() + "----come in");
        });
        System.out.println(future.get());

        CompletableFuture future2 = CompletableFuture.runAsync(()->{
            System.out.println(Thread.currentThread().getName() + "----come in");
        },poolExecutor);
        System.out.println(future2.get());


        CompletableFuture future3 =  CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName() + "----come in");
            return 11;
        });
        System.out.println(future3.get());

        CompletableFuture future4 =  CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName() + "----come in");
            return 12;
        },poolExecutor);
        System.out.println(future4.get());

        poolExecutor.shutdown();







    }

}
