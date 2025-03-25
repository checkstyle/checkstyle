/*
LocalFinalVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;
import java.io.*;
/**
 * Contains simple mistakes:
 * - Long lines
 * - Tabs
 * - Format of variables and parameters
 * - Order of modifiers
 * @author Oliver Burn
 **/
final class InputLocalFinalVariableName
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
    int test1(int badFormat1,int badFormat2,
              final int badFormat3)
        throws java.lang.Exception
    {
        return 0;
    }

    /** method that is 20 lines long **/
    private void longMethod()
    {
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
    }

    /** constructor that is 10 lines long **/
    private InputLocalFinalVariableName()
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

    /** test local variables */
    private void localVariables()
    {
        // normal decl
        int abc = 0;
        int ABC = 0;

        // final decls
        final int cde = 0;
        final int CDE = 0; // violation

        // decl in for loop init statement
        for (int k = 0; k < 1; k++)
        {
            String innerBlockVariable = "";
        }
        for (int I = 0; I < 1; I++)
        {
            String InnerBlockVariable = "";
        }
    }

    /** test method pattern */
    void ALL_UPPERCASE_METHOD()
    {
    }

    /** test illegal constant **/
    private static final int BAD__NAME = 3;

    // A very, very long line that is OK because it matches the regexp "^.*is OK.*regexp.*$"
    // long line that has a tab ->        <- and would be OK if tab counted as 1 char
    // tabs that count as one char because of their position ->        <-   ->        <-, OK

    /** some lines to test the violation column after tabs */
    void errorColumnAfterTabs()
    {
        // with tab-width 8 all statements below start at the same column,
        // with different combinations of ' ' and '\t' before the statement
                int tab0 =1;
                int tab1 =1;
                 int tab2 =1;
                int tab3 =1;
                    int tab4 =1;
                  int tab5 =1;
    }

    // MEMME:
    /* MEMME: a
     * MEMME:
     * OOOO
     */
    /* NOTHING */
    /* YES */ /* MEMME: x */ /* YES!! */

    /** test long comments **/
    void veryLong()
    {
        /*
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          blah blah blah blah
          enough talk */
    }

    /**
     * @see to lazy to document all args. Testing excessive # args
     **/
    void toManyArgs(int aArg1, int aArg2, int aArg3, int aArg4, int aArg5,
                    int aArg6, int aArg7, int aArg8, int aArg9)
    {
    }
}

/** Test class for variable naming in for each clause. */
class InputLocalFinalVariableName2
{
    /** Some more Javadoc. */
    public void doSomething()
    {
        //"O" should be named "o"
        for (Object O : new java.util.ArrayList())
        {

        }
    }
}

/** Test enum for member naming check */
enum InputLocalFinalVariableNameEnum1
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}
