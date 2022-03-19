package com.atguigu.juc.threadLocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/19 18:46
 */
public class ThreadLocalDateUtils {

    public static final SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 线程不安全
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date parse(String str) throws ParseException {
        return sdf.parse(str);
    }
    public static synchronized  Date syncParse(String str) throws ParseException {
        return sdf.parse(str);
    }

    /**
     * 使用DateTimeFormat  更好  localDatetime
     */
    public static  final ThreadLocal<SimpleDateFormat> sdfThread = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static final Date parseByThreadLocal(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = sdfThread.get();
        Date date = simpleDateFormat.parse(str);

        return date;
    }

    public static void main(String[] args) {
        for(int i =0 ; i< 3;i++){
            new Thread(() ->{
                try {
                   // System.out.println( ThreadLocalDateUtils.parse("2022-12-12 11:11:11"));

                    /*SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sdf.parse("2022-12-12 11:11:11");*/

                    System.out.println( ThreadLocalDateUtils.parseByThreadLocal("2022-12-12 11:11:11"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }finally {
                    sdfThread.remove();
                }
            }).start();
        }
    }
}
