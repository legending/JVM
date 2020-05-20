package com.legend.jvm.c3_jmm;

/*
* 证明CPU存在乱序执行的情况
* a = 1; 与 x = b; 之间没有依赖关系
* b = 1; 与 y = a; 之间没有依赖关系
* 因为两个线程都被join过了，所以当执行到
* String result = "第" + i + "次 (" + x + "," + y + "）";
* 时，两个线程都已执行完毕，但无论哪个线程先执行完，都不可能出现x=0,y=0的情况
*
* 当给a/b/x/y加上volatile后问题不再出现（至少测了N久从来没有出现）
* */

public class T04_Disorder {
    private /*volatile*/ static int x = 0, y = 0;
    private /*volatile*/ static int a = 0, b =0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for(;;) {
            i++;
            x = 0; y = 0;
            a = 0; b = 0;
            Thread one = new Thread(new Runnable() {
                public void run() {
                    //由于线程one先启动，下面这句话让它等一等线程two. 读着可根据自己电脑的实际性能适当调整等待时间.
                    //shortWait(100000);
                    a = 1;
                    x = b;
                }
            });

            Thread other = new Thread(new Runnable() {
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            one.start();other.start();
            one.join();other.join();
            String result = "第" + i + "次 (" + x + "," + y + "）";
            if(x == 0 && y == 0) {
                System.err.println(result);
                break;
            } else {
                //System.out.println(result);
            }
        }
    }


    public static void shortWait(long interval){
        long start = System.nanoTime();
        long end;
        do{
            end = System.nanoTime();
        }while(start + interval >= end);
    }
}