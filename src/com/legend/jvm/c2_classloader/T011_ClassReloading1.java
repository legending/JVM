package com.legend.jvm.c2_classloader;

/*
* 双亲委派保证了某个名字的类只会被加载一次，并且一旦加载就不会重复加载
* */

public class T011_ClassReloading1 {
    public static void main(String[] args) throws Exception {
        T006_MyClassLoader myClassLoader = new T006_MyClassLoader("");
        Class clazz = myClassLoader.loadClass("com.legend.jvm.Hello");

        myClassLoader = null;
        System.out.println(clazz.hashCode());

        myClassLoader = null;

        myClassLoader = new T006_MyClassLoader("");
        Class clazz1 = myClassLoader.loadClass("com.legend.jvm.Hello");
        System.out.println(clazz1.hashCode());

        System.out.println(clazz == clazz1);
    }
}
