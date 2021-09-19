/*
RedundantModifier
tokens = STRICTFP, METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierWithStrictfpAsRedundant {
    strictfp void method(){} // violation
    void foo2(){}
}
