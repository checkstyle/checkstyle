/*
MethodCount
maxTotal = 1
maxPrivate = (default)100
maxPackage = (default)100
maxProtected = (default)100
maxPublic = (default)100
tokens = ENUM_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

public class InputMethodCount7 {
    void method1() {
    }

    void method2() {
    }

    enum InnerEnum { // violation 'Total number of methods is 2 (max allowed is 1).'
        ;

        public static void test1() {
            Runnable r = (new Runnable() {
                public void run() {
                    run2();
                }

                private void run2() {
                }
            });
        }

        public static void test2() {
        }
    }
}
