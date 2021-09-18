/*
RedundantModifier
tokens = STRICTFP, METHOD_DEF
strictfpRedundant = true

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierWithStrictfpAsRedundant {
    strictfp void method(){} // violation
    void foo2(){}
}
