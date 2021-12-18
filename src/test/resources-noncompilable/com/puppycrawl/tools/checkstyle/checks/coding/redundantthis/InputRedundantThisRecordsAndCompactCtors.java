/*
RedundantThis
checkFields = false
checkMethods = (default)true


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisRecordsAndCompactCtors {
    public static record MyRecord1(Integer x, String str) {
        static int i;

        public MyRecord1 {
            method1(); // ok
            method2(42); // ok
            method3(); // ok
        }

        void method1() {
            i = 3;
        }

        void method2(int i) {
            i++;
            this.i = i;
            method1(); // ok
            try {
                this.method1(); // violation
            }
            catch (RuntimeException e) {
                e.toString();
            }
            this.i--;

            Integer.toString(10);
        }

        <T> void method3()
        {
            i = 3;
        }

        void method4() {
            this.<String>method3(); // ok
        }
    }

    public static class MyClass {
        static int i;

        public MyClass(int i) {
            method1(); // ok
            method2(i); // ok
            method3(); // ok
        }

        void method1() {
            i = 3;
        }

        void method2(int i) {
            i++;
            this.i = i;
            method1();
            try {
                this.method1(); // violation
            }
            catch (RuntimeException e) {
                e.toString();
            }
            this.i--;

            Integer.toString(10);
        }

        <T> void method3()
        {
            i = 3;
        }

        void method4() {
            this.<String>method3(); // ok
        }
    }
}