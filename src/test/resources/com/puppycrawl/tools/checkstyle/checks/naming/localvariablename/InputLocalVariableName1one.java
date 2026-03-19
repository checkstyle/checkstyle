/*
LocalVariableName

format = (default)^([a-z][a-zA-Z0-9]*|_)$
allowOneCharVarInForLoop = (default)false
allowFinal = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.naming.localvariablename;

import java.io.*;

/**
 * Contains simple mistakes:
 * - Long lines
 * - Tabs
 * - Format of variables and parameters
 * - Order of modifiers
 * @author Oliver Burn
 **/

final class InputLocalVariableName1one {

    public static final int badConstant = 2;
    public static final int MAX_ROWS = 2;

    private static int badStatic = 2;
    private static int sNumCreated = 0;

    private int badMember = 2;
    private int mNumCreated1 = 0;
    protected int mNumCreated2 = 0;

    private int[] mInts = new int[] {1, 2, 3, 4};

    public static int sTest1;
    protected static int sTest3;
    static int sTest2;

    int mTest1;
    public int mTest2;

    int test1(int badFormat1, int badFormat2,
              final int badFormat3)
            throws Exception {
        return 0;
    }

    private void longMethod() {
        // lines
    }
}
