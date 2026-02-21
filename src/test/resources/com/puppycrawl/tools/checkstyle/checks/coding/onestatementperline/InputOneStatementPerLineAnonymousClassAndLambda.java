/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.util.function.Consumer;

public class InputOneStatementPerLineAnonymousClassAndLambda {
    void method() {
        Runnable r1 = new Runnable() {
            @Override
            public void run() { int a =
                1; Consumer<String> c = s -> { System.out.println(s); };
            }
        };
        // violation 3 lines above 'Only one statement per line allowed.'

        Consumer<String> c2 = (str) -> {
            Runnable r2 = new Runnable() {
                @Override public void run() { int x = 10;; }
            }; // violation above 'Only one statement per line allowed.'
        };; // violation 'Only one statement per line allowed.'

        Runnable r3 = new Runnable() { public void run() {} };
        int[] b =
                new int[2]; Runnable r4 = () -> {};
        // violation above 'Only one statement per line allowed.'

        Object obj = new Object();

        Runnable r5 = () -> {
            new Thread(new Runnable() {
                public void run() {
                    Runnable r6 = () -> { int y = 20; };
                }
            }).start();
        };
    }
    void method2() {
        Object l1 = new Object() {
            Consumer<Object> l2 = (obj) -> {
                Object l3 = new Object() {
                    int a = 2; Consumer<String> l4 =
                            s -> { foo();};
                }; // violation above 'Only one statement per line allowed.'
            int b = 1; };
        };
        }
        void foo() {}

int a = 2;}; // violation 'Only one statement per line allowed.'
