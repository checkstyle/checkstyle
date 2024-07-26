/*
RedundantModifier
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE
jdkVersion = 11


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierStrictfpWithOldVersion {

    public static strictfp class Test { }

    strictfp interface MyInterface { }

    strictfp enum MyEnum {}

    strictfp record MyRecord(int x) {}

    abstract strictfp class MyAbstractClass { }

    abstract strictfp interface MyStrictFPInterface {
        // violation above, 'Redundant 'abstract' modifier'
        public static strictfp enum MyInnerEnum { }
        // 2 violations above:
        //                   'Redundant 'public' modifier'
        //                   'Redundant 'static' modifier'
    }

    final class OtherClass {
        final strictfp void m1() {}
        // violation above, 'Redundant 'final' modifier'
    }
}
