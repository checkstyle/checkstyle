/*
RequireThis
checkFields = false
checkMethods = (default)true
validateOnlyOverlapping = false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisRecordsAndCompactCtors {
    public static record MyRecord1(Integer x, String str) {
        static int i; // all fields must be static in a record definition

        public MyRecord1 {
            method1(); // violation
            method2(42); // violation
            method3(); // violation
        }

        void method1() {
            i = 3; // ok, 'i' is static
        }

        void method2(int i) {
            i++;
            this.i = i;
            method1(); // violation
            try {
                this.method1(); // ok
            }
            catch (RuntimeException e) {
                e.toString();
            }
            this.i--;

            Integer.toString(10);
        }

        <T> void method3()
        {
            i = 3; // ok, 'i' is static
        }

        void method4() {
            this.<String>method3(); // ok
        }
    }

    public static class MyClass {
        static int i;

        public MyClass(int i) {
            method1(); // violation
            method2(i); // violation
            method3(); // violation
        }

        void method1() {
            i = 3; // ok, 'i' is static
        }

        void method2(int i) {
            i++;
            this.i = i;
            method1(); // violation
            try {
                this.method1(); // ok
            }
            catch (RuntimeException e) {
                e.toString();
            }
            this.i--;

            Integer.toString(10);
        }

        <T> void method3()
        {
            i = 3; // ok, 'i' is static
        }

        void method4() {
            this.<String>method3(); // ok
        }
    }
}
