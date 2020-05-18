package com.legend.jvm.c2_classloader;
/*
* 加载器使用了模板模式
*
* 查看ClassLoader源码可以看出
* 1.子与父的关系实际上是通过ClassLoader类型的parent属性来提现的
* 2.双亲委派底层是通过递归来实现的
*
* 自定义加载器只需要继承ClassLoader然后重写findClass方法即可
*
* defineClass方法是把类的class二进制流转为对象
*
* 什么时候需要自己加载一个类？
* 1、加密：众所周知，java代码很容易被反编译，如果你需要把自己的代码进行加密，可以先将编译后的代码用某种加密算法加密，然后实现自己的类加载器，负责将这段加密后的代码还原。
* 2、从非标准的来源加载代码：例如你的部分字节码是放在数据库中甚至是网络上的，就可以自己写个类加载器，从指定的来源加载类。
* 3、动态创建：为了性能等等可能的理由，根据实际情况动态创建代码并执行。
*
* 如果用自定义的加载器加载指定类
* （1）避免name与三个父类加载器重名（如com.legend.hello），因为如果重名会使用父类加载器来加载
* （2）这里自定义的加载器只能通过反射的方式来调用外部类，因为无法通过import直接导入，即使你新建了一个类与外部类一模一样，
*      然后外部类对象仍然无法强转成内部类的类型
* （3）做这个实验的时候一定要把测试的类放在项目外的某个位置，并保证package与所在位置是对应的
* */

import com.legend.jvm.Hello;

import java.io.*;
import java.lang.reflect.Method;

public class T006_MyClassLoader extends ClassLoader {

    private String classpath;

    public T006_MyClassLoader(String classpath) {
        this.classpath = classpath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte [] classDate = getDate(name);
            if(classDate==null){}
            else{
                //defineClass方法将字节码转化为类
                return defineClass(name, classDate,0, classDate.length);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        return super.findClass(name);
    }

    //返回类的字节码
    private byte[] getDate(String className) throws IOException{
        InputStream in = null;
        ByteArrayOutputStream out = null;
        String path = classpath + File.separatorChar + className.replace('.', File.separatorChar) + ".class";

        try {
            in = new FileInputStream(path);
            out = new ByteArrayOutputStream();
            byte[] buffer=new byte[2048];
            int len = 0;
            while((len=in.read(buffer)) != -1){
                out.write(buffer,0, len);
            }
            return out.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            in.close();
            out.close();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new T006_MyClassLoader("f:\\Docs");
        Class clazz = classLoader.loadClass("Hello");
        Class clazz1 = classLoader.loadClass("Hello");
        System.out.println(clazz == clazz1);
        System.out.println(clazz.getClassLoader());
        System.out.println(classLoader.getParent());
        System.out.println(getSystemClassLoader());

        if(clazz != null){
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod("m", null);
            method.invoke(obj, null);

            /*Hello hello = (Hello)clazz.newInstance();
            hello.m();*/
        }
    }
}
