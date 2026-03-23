/*
HideUtilityClassConstructor
ignoreAnnotatedBy = (default)

*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

// violation, 'Utility classes should not have a public or default constructor.'

public class InputHideUtilityClassConstructorInnerStaticClasses {
    private static int value = 0;
    public static void foo (int val) { value = val;}

    public static class Inner {
        public int foo;
    }

    public static class Inner2 {
        public static int foo;
    }
}
