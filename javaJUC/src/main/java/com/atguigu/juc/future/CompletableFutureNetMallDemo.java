package com.atguigu.juc.future;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFutureNetMallDemo {

    static List<NetMail> list = Arrays.asList(
            new NetMail("jd"),
            new NetMail("pdd") ,
            new NetMail("tmall"),new NetMail("wuhan")
    );

    // 同步  step by step
    public static List<String> getPriceByStep(List<NetMail> list ,String productName){
        return  list.stream().map(f -> String.format(productName + " in %s price is %.2f",f.getMailName(),f.calcPrice(productName)))
                .collect(Collectors.toList());
    }
    // 异步   List<NetMail> ---->   List<CompletableFuture<String>>   --->  List<String>
    //    //  get() 和join()  get会抛出异常  join不会
    public static List<String> getPriceByAsync(List<NetMail> list ,String productName){
        return list.stream().map(f -> CompletableFuture.supplyAsync(() ->
         String.format(productName + " in %s price is %.2f",f.getMailName(),f.calcPrice(productName)) ))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }



    public static void main(String[] args) throws ExecutionException, InterruptedException {

      long startTime = System.currentTimeMillis();
      List<String> list1 = getPriceByStep(list,"mysql");
      for(String str : list1){
          System.out.println(str);
      }
        long endTime = System.currentTimeMillis();
        System.out.println("---- cosTime +" + (endTime - startTime) + "毫秒");


        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByAsync(list,"mysql");
        for(String str : list2){
            System.out.println(str);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("---- cosTime2 +" + (endTime2 - startTime2) + "毫秒");


    }


    public static void m1(String[] args) throws ExecutionException, InterruptedException {

        int i= CompletableFuture.supplyAsync(() ->{
            return 2;
        }).whenComplete((v,e) ->{
            if(e == null){
                System.out.println(" --- result  " + v);
            }
        }).exceptionally(e ->{
            e.printStackTrace();
            return null;
        }).join();
        //  get() 和join()  get会抛出异常  join不会


        System.out.println(" --- result --- " + i);
        System.out.println(" --- main--over  " );
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }




}
 class NetMail{


    private String mailName;

    public NetMail(String mailName){
        this.mailName = mailName;
    }

    public double calcPrice(String productName){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }

     public String getMailName() {
         return mailName;
     }

     public void setMailName(String mailName) {
         this.mailName = mailName;
     }
 }