package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

/*
 * Config: Default
 */
public class InputJavadocStyleInterfaceScope {
    interface SubClass1 {
        /**
         * Javadoc without dot
         */
        public static void foo1() {} // violation
    }

    protected interface SubClass2{
        /**
         * Javadoc without dot
         */
        public static void foo2() {} // violation
    }

    private interface SubClass3 {
        /**
         * Javadoc without dot
         */
        public static void foo3() {} // violation
    }
}

class PackageClassTwo {
    private interface SubClass {
        /**
         * Javadoc without dot
         */
        public static void foo3 () {} // violation
    }

    protected interface SubClass1 {
        /**
         * Javadoc without dot
         */
        public static void foo4 () {} // violation
    }

    interface SubClass2 {
        /**
         * Javadoc without dot
         */
        public static void foo5 () {} // violation
    }
}
