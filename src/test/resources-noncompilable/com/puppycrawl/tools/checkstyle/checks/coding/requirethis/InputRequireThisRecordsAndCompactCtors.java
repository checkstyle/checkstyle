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
            method1(); // violation 'Method call to 'method1' needs "this.".'
            method2(42); // violation 'Method call to 'method2' needs "this.".'
            method3(); // violation 'Method call to 'method3' needs "this.".'
        }

        void method1() {
            i = 3; // ok, 'i' is static
        }

        void method2(int i) {
            i++;
            this.i = i;
            method1(); // violation 'Method call to 'method1' needs "this.".'
            try {
                this.method1();
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
            this.<String>method3();
        }
    }

    public static class MyClass {
        static int i;

        public MyClass(int i) {
            method1(); // violation 'Method call to 'method1' needs "this.".'
            method2(i); // violation 'Method call to 'method2' needs "this.".'
            method3(); // violation 'Method call to 'method3' needs "this.".'
        }

        void method1() {
            i = 3; // ok, 'i' is static
        }

        void method2(int i) {
            i++;
            this.i = i;
            method1(); // violation 'Method call to 'method1' needs "this.".'
            try {
                this.method1();
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
            this.<String>method3();
        }
    }
}
