package com.atguigu.juc.objectHead;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/21 21:27
 */
public class ObjectHeadDemo {
    public static void main(String[] args) {
      /*  //vm 的细节详细情况
       System.out.println(VM.current().details());

        //vm 的细节详细情况
        System.out.println(VM.current().objectAlignment());*/

        Object object = new Object();
        //引入了JOL 直接使用
        System.out.println(ClassLayout.parseInstance(object).toPrintable());

    }
}
