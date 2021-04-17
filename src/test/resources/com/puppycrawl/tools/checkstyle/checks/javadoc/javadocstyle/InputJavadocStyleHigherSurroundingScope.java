package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

/**
 * Config:
 * "scope" = "private"
 * "excludedScope" = "package"
 */
public class InputJavadocStyleHigherSurroundingScope {
    class SubClass1 {
        /**
         * Javadoc without dot
         */
        public void foo1() {} // ok
    }
    protected class SubClass2{
        /**
         * Javadoc without dot
         */
        public void foo2() {} // ok
    }

    private class SubClass3 {
        /**
         * Javadoc without dot
         */
        public void foo3() {} // violation
    }
}

class PackageClass2 {
    private class SubClass {
        /**
         * Javadoc without dot
         */
        public void foo3 () {} // violation
    }

    protected class SubClass1 {
        /**
         * Javadoc without dot
         */
        public void foo4() {} // ok
    }

    class SubClass2 {
        /**
         * Javadoc without dot
         */
        public void foo5() {} // ok
    }
}
