/*
RedundantModifier
jdkVersion = 11
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public interface InputRedundantModifierNestedDef {
    public enum MyInnerEnum1 {} // violation 'Redundant 'public' modifier.'
    static enum MyInnerEnum2 {} // violation 'Redundant 'static' modifier.'
    public static enum MyInnerEnum3 {}
    // 2 violations above:
    // 'Redundant 'public' modifier.'
    // 'Redundant 'static' modifier.'
    static public enum MyInnerEnum4 {}
    // 2 violations above:
    // 'Redundant 'static' modifier.'
    // 'Redundant 'public' modifier.'

    interface MyInnerInterface {
        public strictfp class MyInnerClass {} // violation 'Redundant 'public' modifier.'
    }

    public static class testClass {
    // 2 violations above:
    // 'Redundant 'public' modifier.'
    // 'Redundant 'static' modifier.'
    }

    public abstract static @interface testAnnotatedInterface {
    // 3 violations above:
    // 'Redundant 'public' modifier.'
    // 'Redundant 'abstract' modifier.'
    // 'Redundant 'static' modifier.'
    }
}

abstract @interface testAnnotatedInterface { // violation 'Redundant 'abstract' modifier.'

    public static enum testEnum {
    // 2 violations above:
    // 'Redundant 'public' modifier.'
    // 'Redundant 'static' modifier.'
    }

    interface testInterface {
        public static interface nestedInterface {
        // 2 violations above:
        // 'Redundant 'public' modifier.'
        // 'Redundant 'static' modifier.'

            public static class nestedClass {
            // 2 violations above:
            // 'Redundant 'public' modifier.'
            // 'Redundant 'static' modifier.'
            }

            public static @interface nestedAnnInterface {
            // 2 violations above:
            // 'Redundant 'public' modifier.'
            // 'Redundant 'static' modifier.'
            }

            public static enum nestedEnum {
            // 2 violations above:
            // 'Redundant 'public' modifier.'
            // 'Redundant 'static' modifier.'
            }
        }
    }
}
