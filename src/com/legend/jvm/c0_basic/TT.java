package com.legend.jvm.c0_basic;

/*
* 通过bytecode查看可知main的底层指令如下
    0 new #3 <com/legend/jvm/c0_basic/TT>
    3 dup
    4 invokespecial #4 <com/legend/jvm/c0_basic/TT.<init>> //执行构造方法进行初始化
    7 astore_1 //将对象的地址赋给符号引用
    8 return
* 4与7可能发生指令重排序，这也是为什么如果DCL中instance属性如果不加volatile会取到半初始化对象的原因
*
* */

public class TT {
    int m = 8;

    public static void main(String[] args) {
        TT t = new TT();
    }
}
