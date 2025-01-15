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
    public final int a[] = {4, 5};

    public int x = 10;
    public int y = 10 * 20;
    public int[] z = {4, 5};

}
