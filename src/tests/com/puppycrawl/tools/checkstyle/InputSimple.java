////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: Feb-2001
// Ignore error
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Contains simple mistakes:
 * - Long lines
 * - Tabs
 * - Format of variables
 * - Format of parameters
 * @author Oliver Burn
 **/
class InputSimple
{
    // Long line ----------------------------------------------------------------
    // Contains a tab ->	<-

    //
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
    public int mTest1;
    /** should be private **/
    int mTest2;

    //
    // Parameter name format tests
    //

    /**
     * @return hack
     * @param badFormat1 bad format
     * @param badFormat2 bad format
     * @param badFormat3 bad format
     * @throws java.lang.Exception abc
     **/
    int test1(int badFormat1,
              int badFormat2,
              final int badFormat3)
        throws java.lang.Exception
    {
        return 0;
    }
}
