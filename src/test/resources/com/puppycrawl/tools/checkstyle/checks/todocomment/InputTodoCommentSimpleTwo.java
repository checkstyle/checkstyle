/* // violation
TodoComment
format = FIXME:


*/

package com.puppycrawl.tools.checkstyle.checks.todocomment;

import java.io.*;
/**
 * Contains simple mistakes:
 * - Long lines
 * - Tabs
 * - Format of variables and parameters
 * - Order of modifiers
 * @author Oliver Burn
 **/

public class InputTodoCommentSimpleTwo {
    /** test local variables */
    private void localVariables()
    {
        // normal decl
        int abc = 0;
        int ABC = 0;

        // final decls
        final int cde = 0;
        final int CDE = 0;

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
    // long line that has a tab ->	<- and would be OK if tab counted as 1 char
    // tabs that count as one char because of their position ->	<-   ->	<-, OK

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

    // FIXME: // violation
    /* FIXME: a // violation
     * FIXME:
     * TODO
     */
    /* NOTHING */
    /* YES */ /* FIXME: x */ /* YES!! */ // violation

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
class InputTodoCommentSimple2
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

