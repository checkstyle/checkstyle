/*
MagicNumber
ignoreNumbers = -2, -1, 0, 1, 2, 100
ignoreHashCodeMethod = true
ignoreFieldDeclaration = (default)false
constantWaiverParentToken = ARRAY_INIT, ASSIGN, ELIST, EXPR
ignoreAnnotation = (default)false
tokens = (default)NUM_DOUBLE,NUM_FLOAT,NUM_INT,NUM_LONG
ignoreAnnotationElementDefaults = (default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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
    public static int[] z = {4, 5, 6, 7};
    // 4 violations above:
    //                    ''4' is a magic number'
    //                    ''5' is a magic number'
    //                    ''6' is a magic number'
    //                    ''7' is a magic number'
    private static final String TEST_TIME =
       OffsetDateTime.of(2023, 11, 11, 11,
       // 4 violations above:
       //                    ''2023' is a magic number'
       //                    ''11' is a magic number'
       //                    ''11' is a magic number'
       //                    ''11' is a magic number'

               11, 11, 11, ZoneOffset.of("Z")).toString();
               // 3 violations above:
               //                    ''11' is a magic number'
               //                    ''11' is a magic number'
               //                    ''11' is a magic number'

    public static int OFFSETOF_NAME = z[3]; // violation ''3' is a magic number'
    public static Object[] STABLE_OBJECT_ARRAY = new Object[4];
    // 1 violations above:
    //                   ''4' is a magic number'
}
