/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierStrictfpWithDefaultVersion {

    public static strictfp class Test { }
    // violation above, 'Redundant 'strictfp' modifier'

    strictfp interface MyInterface { }
    // violation above, 'Redundant 'strictfp' modifier'

    strictfp enum MyEnum {}
    // violation above, 'Redundant 'strictfp' modifier'

    strictfp record MyRecord(int x) {}
    // violation above, 'Redundant 'strictfp' modifier'

    abstract strictfp class MyAbstractClass { }
    // violation above, 'Redundant 'strictfp' modifier'

    abstract strictfp interface MyStrictFPInterface {
        // 2 violations above:
        //                  'Redundant 'abstract' modifier'
        //                  'Redundant 'strictfp' modifier'
        public static strictfp enum MyInnerEnum { }
        // 3 violations above:
        //                   'Redundant 'public' modifier'
        //                   'Redundant 'static' modifier'
        //                   'Redundant 'strictfp' modifier'
    }

    final class OtherClass {
        final strictfp void m1() {}
        // 2 violations above:
        //                  'Redundant 'final' modifier'
        //                  'Redundant 'strictfp' modifier'
    }
}
