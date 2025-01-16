/*
MagicNumber
ignoreHashCodeMethod = (default)false
ignoreFieldDeclaration = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberDefault4 {
    // This should not trigger a violation as it's a constant definition
    private static final int MY_CONSTANT = 42;

    // This should not trigger a violation as it uses the constant
    private final int usingConstant = MY_CONSTANT;

    // This should not trigger a violation as it's a direct constant definition
    private static final int ANOTHER_CONSTANT = 123;

    public void method() {

        int magicNumber = 42; // violation ''42' is a magic number'

        // This should not trigger a violation
        int usingConstantInMethod = MY_CONSTANT;
    }
}

