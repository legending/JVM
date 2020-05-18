package com.legend.jvm.c2_classloader;

/*
* Exception in thread "main" java.lang.ClassFormatError: Truncated class file
* =>文件写入ByteArrayOutputStream方法错误
* */

import com.legend.jvm.Hello;

import java.io.*;
import java.lang.reflect.Method;

public class T007_MyClassLoaderWithEncryption extends ClassLoader {

    public static int seed = 0B10110110;

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = "f:\\Docs\\" + name.replace('.', '\\').concat(".lgclass");
        try {
            InputStream is = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte [] buffer = new byte[1024];
            int len = 0;
            while ((len=is.read(buffer)) != -1) {
                for (int i = 0; i < len; i++) {
                    baos.write(buffer[i]^seed);
                }
            }

            byte[] bytes = baos.toByteArray();
            baos.close();
            is.close();//可以写的更加严谨

            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name); //throws ClassNotFoundException
    }

    public static void main(String[] args) throws Exception {

        encryptFile("Hello");

        ClassLoader classLoader = new T007_MyClassLoaderWithEncryption();
        Class clazz = classLoader.loadClass("Hello");
        System.out.println(clazz.getClassLoader());
        System.out.println(classLoader.getParent());

        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("m", null);
        method.invoke(obj, null);
        /*Hello h = (Hello)clazz.newInstance();
        h.m();*/
    }

    private static void encryptFile(String name) throws Exception {
        File f = new File("f:\\Docs\\", name.replace('.', '\\').concat(".class"));
        InputStream is = new FileInputStream(f);
        FileOutputStream fos = new FileOutputStream(new File("f:\\Docs\\", name.replace(".", "\\").concat(".lgclass")));
        byte [] buffer = new byte[1024];
        int len =0;
        while ((len=is.read(buffer)) != -1) {
            for (int i=0; i<len; i++) {
                fos.write(buffer[i]^seed);
            }
        }

        is.close();
        fos.close();
    }
}
