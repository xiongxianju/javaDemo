package com.atguigu.juc.rwlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * 邮戳锁  票据锁
 *  乐观读  ----   读的时候可以进去写  可以读写混合   数据一致性问题
 *  不支持重入   容易导致死锁
 * @Author: xiongxianju
 * @Date: 2022/3/27 11:54
 */
public class StampedLockDemo {

    static int number = 37;
    static StampedLock stampedLock = new StampedLock();

    public void write(){
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName() + " === 写线程准备修改");
        try{
            number = number +13 ;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            stampedLock.unlockWrite(stamp);
        }
        System.out.println(Thread.currentThread().getName() + " === 写线程结束修改");
    }

    /**
     * 悲观读
     */
    public void read(){
        long stamp = stampedLock.readLock();
        System.out.println(Thread.currentThread().getName() + " === 读线程准备 4秒");
        for(int i = 0; i < 4 ; i++){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " === 读线程正在读取中");
        }

        try{
            int result = number ;
            System.out.println(Thread.currentThread().getName() + " === 读线程读取result = " + result);
            System.out.println("写线程没有修改值  因为 stampedLock.readLock() 读取的时候    不可以写   读写互斥");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            stampedLock.unlockRead(stamp);
        }
        System.out.println(Thread.currentThread().getName() + " === 写线程结束修改");
    }

    /**
     * 乐观读
     */
    public void tryOptimisticRead(){
        long stamp = stampedLock.tryOptimisticRead();
        //先把数据获取一次
        int result = number;
        //间隔4秒  我们很乐观认为没有其他线程修改过number 值
        System.out.println( " 4秒前stampedLock.validate值（true无修改   false有修改）"
                + stampedLock.validate(stamp) +"  result ==   "+ result );
        for(int i = 0; i < 4 ; i++){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " === 读线程正在读取中" + i +"秒   " +
                    "stamp有无修改 " + stampedLock.validate(stamp));
        }

        if(!stampedLock.validate(stamp)){
            System.out.println("有人修改过值 ----  存在操作");
            // 有人修改过   就需要从乐观锁切换到悲观锁
            stamp = stampedLock.readLock();
            try{
                System.out.println("从乐观读 升级为悲观读 并重新获取数据");
                //重新获取数据
                result = number;

                System.out.println("重新悲观读锁或者数据值为 result = " + result);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                stampedLock.unlockRead(stamp);
            }
        }

        System.out.println(Thread.currentThread().getName() + " === finally value = " + result);
    }

    public static void main(String[] args) {
        StampedLockDemo resource = new StampedLockDemo();

    /*    new Thread(() ->{
            //1 悲观读
            resource.read();
        },"readThread").start();*/

      /*  new Thread(() ->{
            //2 乐观读
            resource.tryOptimisticRead();
        },"readThread2").start();*/

        /**
         * 3   乐观锁失败 重新转悲观锁   重新获取值
         */

        new Thread(() ->{
            //乐观读
            resource.tryOptimisticRead();
        },"readThread3").start();

        try {TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
        new Thread(() ->{
            resource.write();
        },"writeThread").start();
    }



}
