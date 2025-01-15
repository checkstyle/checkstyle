/*
MagicNumber
ignoreNumbers = -2, -1, 0, 1, 2, 100
ignoreHashCodeMethod = true
ignoreFieldDeclaration = true
constantWaiverParentToken = ARRAY_INIT, ASSIGN, ELIST, EXPR

*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberIgnoreFieldDeclaration4 {
    public final int radius = 10;
    public final double area = 22 / 7.0 * radius * radius;
    // 2 violations above, but it should be ok because
    // ignoreFieldDeclaration = true
}
