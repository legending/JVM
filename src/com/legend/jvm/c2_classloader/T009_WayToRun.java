package com.legend.jvm.c2_classloader;

/*
* JVM的三种执行模式
* -Xmixed 默认为混合模式，开始解释执行，启动速度较快，对热点代码实行检测和编译
*  -Xint 使用编译模式，启动很快，执行稍慢
* -Xcomp 使用纯编译模式，执行很快，启动很慢
*
* */

public class T009_WayToRun {
    public static void main(String[] args) {
        for(int i=0; i<10_0000; i++)
            m();

        long start = System.currentTimeMillis();
        for(int i=0; i<10_0000; i++) {
            m();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void m() {
        for(long i=0; i<10_0000L; i++) {
            long j = i%3;
        }
    }
}
