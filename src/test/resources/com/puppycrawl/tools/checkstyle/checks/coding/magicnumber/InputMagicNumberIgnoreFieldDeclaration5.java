/*
MagicNumber
ignoreNumbers = -2, -1, 0, 1, 2, 100
ignoreHashCodeMethod = true
ignoreFieldDeclaration = (default)false
constantWaiverParentToken = ARRAY_INIT, ASSIGN, ELIST, EXPR

*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberIgnoreFieldDeclaration5 {
    public final int radius = 10;
    public final double area = 22 / 7.0 * radius * radius;
    // 2 violations above:
    //                    ''22' is a magic number'
    //                    ''7.0' is a magic number'
    public final int a[] = {4, 5};

    public int x = 10; // violation ''10' is a magic number'
    public int y = 10 * 20;
    // 2 violations above:
    //                    ''10' is a magic number'
    //                    ''20' is a magic number'
    public int[] z = {4, 5};
    // 2 violations above:
    //                    ''4' is a magic number'
    //                    ''5' is a magic number'

}
