/*
MethodLength
max = 19
countEmpty = (default)true
tokens = (default)METHOD_DEF , CTOR_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;
import java.io.*;

final class InputMethodLengthSimpleTwo
{
    /** method that is 20 lines long **/
    // violation below 'Method longMethod length is 20 lines (max allowed is 19)'
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

/** Test enum for member naming check */
enum MyEnum1
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}
