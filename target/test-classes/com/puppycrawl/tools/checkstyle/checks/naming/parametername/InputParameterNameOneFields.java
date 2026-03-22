/*
ParameterName
format = ^a[A-Z][a-zA-Z0-9]*$
ignoreOverridden = (default)false
accessModifiers = (default)public, protected, package, private


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;
import java.io.*;

public final class InputParameterNameOneFields
{
    // Long line ----------------------------------------------------------------
    // Contains a tab ->        <-
    // Contains trailing whitespace ->

    // Name format tests
    //
    /** Invalid format **/
    public static final int badConstant = 2;
    /** Valid format **/
    public static final int MAX_ROWS = 2;

    /** Invalid format **/
    private static int badStatic = 2;
    /** Valid format **/
    private static int sNumCreated = 0;

    /** Invalid format **/
    private int badMember = 2;
    /** Valid format **/
    private int mNumCreated1 = 0;
    /** Valid format **/
    protected int mNumCreated2 = 0;

    /** commas are wrong **/
    private int[] mInts = new int[] {1,2, 3,
                                     4};

    //
    // Accessor tests
    //
    /** should be private **/
    public static int sTest1;
    /** should be private **/
    protected static int sTest3;
    /** should be private **/
    static int sTest2;

    /** should be private **/
    int mTest1;
    /** should be private **/
    public int mTest2;
}
