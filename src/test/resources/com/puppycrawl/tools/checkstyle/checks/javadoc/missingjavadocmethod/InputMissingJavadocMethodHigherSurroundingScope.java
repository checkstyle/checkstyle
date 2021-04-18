package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/**
 * Config:
 * "scope" = "private"
 * "excludedScope" = "package"
 */
public class InputMissingJavadocMethodHigherSurroundingScope {
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

class PackageClass4  {
    private interface SubClass4 {
        public static void foo3 () {} // violation
    }

    protected interface SubClass5 {
        public static void foo4() {} // violation
    }

    interface SubClass6 {
        public static void foo5() {} // violation
    }
}
