package com.legend.jvm.c2_classloader;

/*
* 严格讲应该叫lazy initialzing，因为java虚拟机规范并没有严格规定什么时候必须loading,但严格规定了什么时候initialzing
*
* - new getstatic putstatic invokestatic指令，访问final变量除外
* - java.lang.reflect对类进行反射调用时
* - 初始化子类的时候，父类首先初始化
* - 虚拟机启动时，被执行的主类必须初始化
* - 动态语言支持java.lang.invoke.MethodHandle解析的结果为REF getstatic REF putstatic REF invokestatic的方法句柄时，该类必须初始化
* */

public class T008_LazyLoading {
    public static void main(String[] args) throws Exception {
        //P p;
        //X x = new X();
        //System.out.println(P.i);//调用final变量时不需要加载任何类
        System.out.println(X.j);//调用父类的静态变量时，只会加载父类不会加载本类
        //System.out.println(P.j);
        //Class.forName("com.legend.jvm.c2_classloader.T008_LazyLoading$P");//手动加载某个类

    }

    public static class P {
        final static int i = 8;
        static int j = 9;
        static {
            System.out.println("P");
        }
    }

    public static class X extends P {
        static {
            System.out.println("X");
        }
    }
}
