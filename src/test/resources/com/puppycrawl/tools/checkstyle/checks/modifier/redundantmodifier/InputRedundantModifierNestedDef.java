/*
RedundantModifier
jdkVersion = 11
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE

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

    interface testInterface {
        public static interface nestedInterface { // 2 violations

            public static class nestedClass { // 2 violations
            }

            public static @interface nestedAnnInterface { // 2 violations
            }

            public static enum nestedEnum { // 2 violations
            }
        }
    }
}
