package com.legend.jvm;

public class TestGC {
    public static void main(String[] args) {
        for(;;) {
            new Object();
        }
    }
}
