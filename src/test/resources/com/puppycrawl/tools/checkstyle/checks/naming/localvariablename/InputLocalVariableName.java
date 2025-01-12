/*
LocalVariableName
format = (default)^[a-z][a-zA-Z0-9]*$
allowOneCharVarInForLoop = (default)false


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
final class InputLocalVariableName
{
    /**
     * This file was split into the following:
     * - InputLocalVariableNameMethods1.java: Tests for parameter naming and long methods.
     * - InputLocalVariableNameMethods2.java: Tests for local variables, method patterns, and formatting.
     * Please refer to those files for specific tests.
     */

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


    /** constructor that is 10 lines long **/
    private InputLocalVariableName()
    {
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
    }

}

/** Test class for variable naming in for each clause. */
class InputLocalVariableName2
{
    /** Some more Javadoc. */
    public void doSomething()
    {
        //"O" should be named "o"
        for (Object O : new java.util.ArrayList()) // violation
        {

        }
        for (final int k_ : new int[] {}) {}
    }
}

/** Test enum for member naming check */
enum InputLocalVariableNameEnum1
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}
