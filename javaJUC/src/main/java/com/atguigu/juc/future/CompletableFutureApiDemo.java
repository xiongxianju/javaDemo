package com.atguigu.juc.future;

import java.util.concurrent.*;

/**
 * @author xxj98
 */
public class CompletableFutureApiDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,20,1, TimeUnit.SECONDS,new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        int  tt = CompletableFuture.supplyAsync(() ->{
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() ->{
            return 20;
        }),(r1,r2) ->{
            return r1 +r2;
        }).join();

        System.out.println(" ----- " + tt);
        poolExecutor.shutdown();
    }

    public static void m4(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,20,1, TimeUnit.SECONDS,new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        int j = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }).applyToEither( CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        }),r ->{
            return r;
        }).join();

        System.out.println("--- result " +j);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        poolExecutor.shutdown();
    }



    public static void handle(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,20,1, TimeUnit.SECONDS,new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        Integer i  = CompletableFuture.supplyAsync(()->{
            System.out.println("---  1");
            return 1;
        }).handle((f,e) ->{
            System.out.println("---  2");
            return f+2;
        }).handle( (f,e) ->{
            System.out.println("---  3");
            return  f+3;
        } ).whenComplete((f,e) ->{
            if(e == null){
                System.out.println("--- result "+ f);
            }
        }).exceptionally(e ->{
            e.printStackTrace();
            return null;
        }).join();

        System.out.println("--- result "+ i);




        Integer iiii  = CompletableFuture.supplyAsync(()->{
            return 1;
        }).thenApply(f ->{
            int t = 10/0;
            return f+2;
        }).thenApply( f ->{
            return  f+3;
        } ).whenComplete((f,e) ->{
            if(e == null){
                System.out.println("--- result "+ f);
            }
        }).exceptionally(e ->{
            e.printStackTrace();
            return null;
        }).join();

        System.out.println("--- result "+ iiii);

        poolExecutor.shutdown();
    }


    public static void m1(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,20,1, TimeUnit.SECONDS,new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture future = CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        //   System.out.println(future.get());
        // System.out.println(future.get(2L,TimeUnit.SECONDS));
        // System.out.println(future.getNow(2)); //没获取到就取默认的

   /*
      //  complete 打断   打断成功 还回-44  失败还回原值
      try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(future.complete(-44) +"\t" + future.get());*/




        poolExecutor.shutdown();
    }

}
