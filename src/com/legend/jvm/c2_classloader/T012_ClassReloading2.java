package com.legend.jvm.c2_classloader;

/*
* 通过重写ClassLoader的loadClass方法可以打破双亲委派机制，从而实现热加载（使用的是两个不同的ClassLoader对象，从而已加载的类可以重新被加载）
* 当ClassLoader对象被干掉之后，它加载的类就会被GC回收
* */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class T012_ClassReloading2 {
    private static class MyLoader extends ClassLoader {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {

            File f = new File("F:/Repos/JVM/out/production/JVM/" + name.replace(".", "/").concat(".class"));

            if(!f.exists()) return super.loadClass(name);

            try {

                InputStream is = new FileInputStream(f);

                byte[] b = new byte[is.available()];
                is.read(b);
                return defineClass(name, b, 0, b.length);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return super.loadClass(name);
        }
    }

    public static void main(String[] args) throws Exception {
        MyLoader m = new MyLoader();
        Class clazz = m.loadClass("com.legend.jvm.Hello");

        m = new MyLoader();
        Class clazzNew = m.loadClass("com.legend.jvm.Hello");

        System.out.println(clazz == clazzNew);
    }
}
