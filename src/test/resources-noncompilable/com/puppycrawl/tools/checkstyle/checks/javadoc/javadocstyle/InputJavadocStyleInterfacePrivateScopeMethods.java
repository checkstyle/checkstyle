//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

/*
 * Config:
 * scope = public
 */
public class InputJavadocStyleInterfacePrivateScopeMethods {
    interface SubClass1 {
        /**
         * Javadoc without dot
         */
        private static void foo1() {} // ok
    }

    protected interface SubClass2{
        /**
         * Javadoc without dot
         */
        private static void foo2() {} // ok
    }

    private interface SubClass3 {
        /**
         * Javadoc without dot
         */
        private static void foo3() {} // ok
    }
}

class PackageClassTwo {
    private interface SubClass {
        /**
         * Javadoc without dot
         */
        private static void foo3 () {} // ok
    }

    protected interface SubClass1 {
        /**
         * Javadoc without dot
         */
        private static void foo4 () {} // ok

    }

    interface SubClass2 {
        /**
         * Javadoc without dot
         */
        private static void foo5 () {} // ok
    }
}
