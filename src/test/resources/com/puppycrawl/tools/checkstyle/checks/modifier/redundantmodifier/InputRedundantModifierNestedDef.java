/*
RedundantModifier


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public interface InputRedundantModifierNestedDef {
    public enum MyInnerEnum1 {} // violation
    static enum MyInnerEnum2 {} // violation
    public static enum MyInnerEnum3 {} // 2 violations
    static public enum MyInnerEnum4 {} // 2 violations

    interface MyInnerInterface {
        public strictfp class MyInnerClass {} // violation
    }

    public static class testClass { // 2 violations
    }

    public abstract static @interface testAnnotatedInterface { // 3 violations
    }
}

abstract @interface testAnnotatedInterface { // violation

    public static enum testEnum { // 2 violations
    }
}
