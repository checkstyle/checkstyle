package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/*
 * Config:
 * "scope" = "private"
 * "excludedScope" = "package"
 */
public class InputMissingJavadocMethodInterfaceScope {
    interface SubClass1 {
        public static void foo1() {} // violation
    }

    protected interface SubClass2{
        public static void foo2() {} // violation
    }

    private interface SubClass3 {
        public static void foo3() {} // violation
    }
}

class PackageClassTwo {
    private interface SubClass {
        public static void foo3 () {} // violation
    }

    protected interface SubClass1 {
        public static void foo4 () {} // violation
    }

    interface SubClass2 {
        public static void foo5 () {} // violation
    }
}
