//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/*
 * Config:
 * scope = public
 */
public class InputMissingJavadocMethodInterfacePrivateScope {
    interface SubClass1 {
        private static void foo1() {} // ok
    }

    protected interface SubClass2{
        private static void foo2() {} // ok
    }

    private interface SubClass3 {
        private static void foo3() {} // ok
    }
}

class PackageClassTwo {
    private interface SubClass {
        private static void foo3 () { // ok
        }
    }

    protected interface SubClass1 {
        private static void foo4 () { // ok
        }
    }

    interface SubClass2 {
         private static void foo5 () { // ok
        }
    }
}
