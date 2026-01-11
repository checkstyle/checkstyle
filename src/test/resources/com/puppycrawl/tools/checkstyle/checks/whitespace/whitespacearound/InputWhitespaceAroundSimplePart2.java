/*
WhitespaceAround
allowEmptyConstructors = (default)false
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
ignoreEnhancedForColon = (default)true
tokens = (default)ASSIGN, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR, \
         BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, LAMBDA, LAND, \
         LCURLY, LE, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, \
         LITERAL_FOR, LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, \
         LITERAL_TRY, LITERAL_WHILE, LOR, LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN, \
         NOT_EQUAL, PLUS, PLUS_ASSIGN, QUESTION, RCURLY, SL, SLIST, SL_ASSIGN, SR, \
         SR_ASSIGN, STAR, STAR_ASSIGN, LITERAL_ASSERT, TYPE_EXTENSION_AND


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundSimplePart2 {

    /**
     * method that is 20 lines long
     **/
    private void longMethod() {
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

    /**
     * constructor that is 10 lines long
     **/
    private InputWhitespaceAroundSimplePart2() {
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
    }

    /**
     * test local variables
     */
    private void localVariables() {
        // normal decl
        int abc = 0;
        int ABC = 0;

        // final decls
        final int cde = 0;
        final int CDE = 0;

        // decl in for loop init statement
        for (int k = 0; k < 1; k++) {
            String innerBlockVariable = "";
        }
        for (int I = 0; I < 1; I++) {
            String InnerBlockVariable = "";
        }
    }

    /**
     * test method pattern
     */
    void ALL_UPPERCASE_METHOD() {
    }

    /**
     * test illegal constant
     **/
    private static final int BAD__NAME = 3;

    // A very, very long line that is OK because it matches the regexp "^.*is OK.*regexp.*$"
    // long line that has a tab ->  <- and would be OK if tab counted as 1 char
    // tabs that count as one char because of their position ->     <-   ->     <-, OK

    /**
     * some lines to test the violation column after tabs
     */
    void errorColumnAfterTabs() {
        // with tab-width 8 all statements below start at the same column,
        // with different combinations of ' ' and '\t' before the statement
                int tab0 =1; // violation ''=' is not followed by whitespace'
            int tab1 =1; // violation ''=' is not followed by whitespace'
            int tab2 =1; // violation ''=' is not followed by whitespace'
        int tab3 =1; // violation ''=' is not followed by whitespace'
        int tab4 =1; // violation ''=' is not followed by whitespace'
            int tab5 =1; // violation ''=' is not followed by whitespace'
    }

}
