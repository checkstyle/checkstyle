/*
RedundantModifier
tokens = STRICTFP, METHOD_DEF, INTERFACE_DEF, ENUM_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

//non-compiled with javac: Compilable with Java14
public class InputRedundantModifierWithStrictfpAsRedundant {
    public strictfp class Test {} // violation

    strictfp interface MyInterface {} // violation

    strictfp enum MyEnum {} // violation

    strictfp record MyRecord() {} // violation

    abstract strictfp class MyClass {} // violation

    abstract strictfp interface MyStrictFPInterface { // 2 violations
        public static strictfp enum MyInnerEnum {} // 2 violations
    }

    final class OtherClass {
        final strictfp void m1() {} // 2 violations
    }
}
