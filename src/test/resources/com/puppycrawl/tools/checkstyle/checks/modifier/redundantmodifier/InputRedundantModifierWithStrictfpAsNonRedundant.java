/*
RedundantModifier
tokens = STRICTFP, METHOD_DEF
strictfpRedundant = false

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierWithStrictfpAsNonRedundant {
    strictfp void method(){} // ok
    void foo2(){}
}
