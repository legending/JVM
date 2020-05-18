package com.legend.jvm.c2_classloader;

/*
* 通过构造方法指定parent加载器
* */

public class T010_Parent {
    private static T006_MyClassLoader parent = new T006_MyClassLoader("");

    private static class MyLoader extends ClassLoader {
        public MyLoader() {
            super(parent);
        }
    }
}
