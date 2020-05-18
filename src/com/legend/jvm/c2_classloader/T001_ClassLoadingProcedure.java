package com.legend.jvm.c2_classloader;

/*
* 请问交换15,16行的代码，输出的结果是什么？
* 1. 主要看准备与初始化阶段
*    准备阶段：静态变量赋默认值
*    初始化阶段：静态变量赋初始值
* 2. 初始化时从上到下按顺序进行
*
* 类似的在new对象的时候也分为两步
* 1. 为对象分配内存赋初始值
* 2. 给成员变量赋初始值
* */

public class T001_ClassLoadingProcedure {
    public static void main(String[] args) {
        System.out.println(T.count);
    }
}

class T {
    public static T t = new T(); // null
    public static int count = 2; //0

    //private int m = 8;

    private T() {
        count ++;
        //System.out.println("--" + count);
    }
}
