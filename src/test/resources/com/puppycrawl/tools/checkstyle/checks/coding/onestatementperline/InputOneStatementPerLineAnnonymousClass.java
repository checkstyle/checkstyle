/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineAnnonymousClass {
    public void method() {

        Runnable r1 = new Runnable() { @Overridepublic void run() {int a = 2;}};

        // violation below 'Only one statement per line allowed.'
        int c = 6; Runnable r4 = new Runnable() { @Overridepublic void run() {}};

        Runnable r2 = new Runnable()
           { @Override public void run() {int d = 2;}}; int b = 0;
        // violation above 'Only one statement per line allowed.'

        int e
        = 6; Runnable r3 = new Runnable() {
         @Overridepublic void run() {int f = 2;}};
        // violation above 'Only one statement per line allowed.'

        Runnable r5 = new Runnable() { @Overridepublic void run() {int h = 2;}};;
        // violation above 'Only one statement per line allowed.'
    }

    void method2() {
        Runnable outer = new Runnable() {
            @Override
            public void run() {
                int i = 1;
                Runnable inner = new Runnable() {
                    @Override
                    public void run() {
                        int j = 2;
                    }
                };
            }
        };
    }
}
