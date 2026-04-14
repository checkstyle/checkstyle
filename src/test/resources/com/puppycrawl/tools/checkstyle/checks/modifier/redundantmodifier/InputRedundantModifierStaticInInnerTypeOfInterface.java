/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public interface InputRedundantModifierStaticInInnerTypeOfInterface {
    static class MyInnerClass { } // violation

    class MyInnerClass2 { }

    static enum MyInnerEnum { } // violation

    enum MyInnerEnum2 { }
}
