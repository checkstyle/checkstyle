package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/**
 * Config:
 * "scope" = "private"
 * "excludedScope" = "package"
 */
public class InputMissingJavadocMethodHigherSurroundingScope {
    class SubClass1 {
        public void foo1() {} // violation
    }
    protected class SubClass2{
        public void foo2() {} // violation
    }

    private class SubClass3 {
        public void foo3() {} // violation
    }
}

class PackageClass4  {
    private class SubClass4 {
        public void foo3 () {} // violation
    }

    protected class SubClass5 { // ok
        public void foo4() {}
    }

    class SubClass6 {
        public void foo5() {} // ok
    }
}
