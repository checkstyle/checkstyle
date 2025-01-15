/*
MagicNumber
ignoreHashCodeMethod = (default)false
ignoreFieldDeclaration = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberDefault4 {

    private static final int DEFAULT_RADIUS = 5;

    final int radius;

    public InputMagicNumberDefault4(int radius) {
        // Using a valid constant; no violation here
        this.radius = DEFAULT_RADIUS;

        int area = 42; // violation ''42' is a magic number'
    }
}
