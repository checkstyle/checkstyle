/*
MagicNumber
ignoreHashCodeMethod = (default)false
ignoreFieldDeclaration = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberDefault4 {
    private static final int DIRECT_CONSTANT = 42;

    // Using constant in an expression - should never trigger a violation
    private static final int EXPRESSION_CONSTANT = DIRECT_CONSTANT + 10;

    private static final int COMPLEX_CONSTANT = DIRECT_CONSTANT * 2 + 15;

    public void method() {
        int magicNumber = 42; // violation ''42' is a magic number'

        // Should not trigger violation - using constant
        int usingConstant = DIRECT_CONSTANT;

        // Using constant in expression - should not trigger
        int complexExpression = DIRECT_CONSTANT * 2;
    }
}

