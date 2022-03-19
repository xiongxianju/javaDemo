package com.atguigu.juc.threadLocal;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/19 15:32
 */
class MovieTicket{
    int number = 50;
    public synchronized void saleTicket(){
        if(number > 0 ){
            System.out.println(Thread.currentThread().getName() +"卖出第  " + (number--)  +"张");
        }else{
            System.out.println("卖光了");
        }
    }
}

class House{
    int i = 0;
    //每个人都有自己卖房子数量
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(()->0);

    public void saleHouse(){
        Integer value = threadLocal.get();
        value ++ ;
        threadLocal.set(value);
    }

}
public class ThreadLocalDemo {
    public static void main(String[] args) {
        /*MovieTicket movieTicket = new MovieTicket();
        for(int i = 0 ; i< 3 ;i++){
            new Thread(() ->{
                for(int j =0 ;j<20 ;j++){
                    movieTicket.saleTicket();
                }
            },String.valueOf(i)).start();
        }*/


        House house = new House();
        new Thread(() ->{
            try {
                for (int i = 0; i < 3; i++) {
                    house.saleHouse();

                }
                System.out.println(Thread.currentThread().getName() + "卖出第  " +
                        house.threadLocal.get() + "套");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                house.threadLocal.remove();
            }

        },"t1").start();

        new Thread(() ->{
            try {
                for (int i = 0; i < 5; i++) {
                    house.saleHouse();

                }
                System.out.println(Thread.currentThread().getName() + "卖出第  " +
                        house.threadLocal.get() + "套");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                house.threadLocal.remove();
            }

        },"t2").start();


        new Thread(() ->{
            try{
            for(int i = 0 ; i< 8;i++){
                house.saleHouse();

            }
            System.out.println(Thread.currentThread().getName() +"卖出第  "+
                    house.threadLocal.get() +"套");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                house.threadLocal.remove();
            }

        },"t3").start();


        System.out.println(Thread.currentThread().getName() +"卖出第  "+
                house.threadLocal.get() +"套");
    }
}
