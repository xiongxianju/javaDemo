package com.atguigu.juc.atomics;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/17 21:31
 */
public class LongAdderAPIDemo {

    public static void main(String[] args) {
        //  LongAdder 只能做加法
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        System.out.println(longAdder.longValue());


        LongAccumulator longAccumulator = new LongAccumulator((x,y) -> x+y,0);
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(2);
        longAccumulator.accumulate(3);

        System.out.println(longAccumulator.longValue());


    }
}
