package com.legend.jvm.c4_RuntimeDataAreaAndInstructionSet;

/*
* 通过jclasslib view 查看i++与++i的操作
* i在操作数栈与局部变量表中的操作不同，导致了不同的结果
* 具体是 iinc 1 by 1 与 iload_1 指令的顺序不同造成的
*
* iinc : int inrease
* istore_<n> ： 出栈
* iload_<n> : 入栈
*
*  i = i++
 0 bipush 8
 2 istore_1
 3 iload_1
 4 iinc 1 by 1
 7 istore_1
 8 getstatic #2 <java/lang/System.out>
11 iload_1
12 invokevirtual #3 <java/io/PrintStream.println>
15 return
*
*
* i = ++i
 0 bipush 8
 2 istore_1
 3 iinc 1 by 1
 6 iload_1
 7 istore_1
 8 getstatic #2 <java/lang/System.out>
11 iload_1
12 invokevirtual #3 <java/io/PrintStream.println>
15 return
* */

public class TestIPulsPlus {
    public static void main(String[] args) {
        int i = 8;
        i = i++;
        //i = ++i;
        System.out.println(i);
    }
}


