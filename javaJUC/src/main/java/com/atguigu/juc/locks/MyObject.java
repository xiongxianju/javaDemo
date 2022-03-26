package com.atguigu.juc.locks;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/23 15:48
 */
public class MyObject {

    public static void main(String[] args) {

    }


    public static void noLock(String[] args) {
        Object o = new Object();
        System.out.println(o.hashCode());
        //16进制
        System.out.println(Integer.toHexString(o.hashCode() ));
        //2进制
        System.out.println(Integer.toBinaryString(o.hashCode() ));
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

    }
}
