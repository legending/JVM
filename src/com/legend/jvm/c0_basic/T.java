package com.legend.jvm.c0_basic;

import java.util.ArrayList;
import java.util.List;

/*
* new一个Object，它到底占用多少字节？
* 设定VM参数：-Xms500M -Xmx500M -XX:+PrintCommandLineFlags，然后反复调整生成的obj个数，直到刚好补发生oom的次数为2076_0000（精确到万位）
* 对象个数越多越精确，最后通过二分法精确定位到2076_7725个对象刚好占用500M内存（因为当改为2076_7726后便会oom）
* 此时每个对象占有19Byte
*
* */

public class T {
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        Runtime r = Runtime.getRuntime();
        long l1 = r.freeMemory();
        for (int i = 0; i < 2076_7725; i++) {
            list.add(new Object());
        }
        long l2 = r.freeMemory();
        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.println((l1 - l2)/2076_7725);
    }
}
