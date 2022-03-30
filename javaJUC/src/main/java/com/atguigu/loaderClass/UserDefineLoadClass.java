package com.atguigu.loaderClass;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author: xiongxianju
 * @Date: 2022/3/29 9:39
 */
public class UserDefineLoadClass extends  ClassLoader{

    private String rootPath;

    public UserDefineLoadClass(String rootPath){
        this.rootPath = rootPath;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //转换为以文件路径标识的文件
        String filePath = classToFilePath(name);

        // 获取指定路径的class文件 对应的二进制数据
        byte[] data = getBytesFromPath(filePath);
        //自定义classLoad
       return defineClass(name,data,0,data.length);
    }
    private String classToFilePath(String name){
        return rootPath + "\\" + name.replace(".","\\")+".class";
    }

    private byte[] getBytesFromPath(String filePath){
        FileInputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            inputStream = new FileInputStream(filePath);
             baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len ;
            while((len = inputStream.read(buffer)) != -1){
                baos.write(buffer,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
                if(baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  baos.toByteArray();
    }

    public static void main(String[] args) {
        try {
            UserDefineLoadClass loadClass = new UserDefineLoadClass("D:\\workspace\\javaDemo\\javaJUC\\target\\classes");
            Class user = loadClass.findClass("com.atguigu.loaderClass.User");
            System.out.println(user);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
