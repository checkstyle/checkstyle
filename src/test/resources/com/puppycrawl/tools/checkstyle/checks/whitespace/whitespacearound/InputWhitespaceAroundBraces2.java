/*
WhitespaceAround
allowEmptyConstructors = true
allowEmptyMethods = true
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
ignoreEnhancedForColon = (default)true
allowEmptySwitchBlockStatements = (default)false
tokens = (default)ASSIGN, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR, \
         BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, LAMBDA, LAND, \
         LCURLY, LE, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, \
         LITERAL_FOR, LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, \
         LITERAL_TRY, LITERAL_WHILE, LOR, LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN, \
         NOT_EQUAL, PLUS, PLUS_ASSIGN, QUESTION, RCURLY, SL, SLIST, SL_ASSIGN, SR, \
         SR_ASSIGN, STAR, STAR_ASSIGN, LITERAL_ASSERT, TYPE_EXTENSION_AND


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

class InputWhitespaceAroundBraces2
{
    /** @return helper func **/
    boolean condition()
    {
        return false;
    }

    /** Test do/while loops **/
    void testDoWhile()
    {
        // Valid
        do {
            testDoWhile();
        }
        while (condition());

        // Invalid
        do testDoWhile(); while (condition());
    }

    /** Test while loops **/
    void testWhile()
    {
        // Valid
        while (condition()) {
            testWhile();
        }

        // Invalid
        while(condition()); // violation ''while' is not followed by whitespace'
        while (condition())
            testWhile();
        while (condition())
            if (condition())
                testWhile();
    }

    /** Test for loops **/
    void testFor()
    {
        // Valid
        for (int i = 1; i < 5; i++) {
            testFor();
        }

        // Invalid
        for(int i = 1;i < 5;i++); // violation ''for' is not followed by whitespace'
        for (int i = 1; i < 5; i++)
            testFor();
        for (int i = 1; i < 5;
             i++)
            if (i > 2)
                testFor();
    }

    /** Test if constructs **/
    public void testIf()
    {
        // Valid
        if (condition()) {
            testIf();
        }
        else if (condition()) {
            testIf();
        }
        else {
            testIf();
        }

        // Invalid
        if (condition());
        if (condition())
            testIf();
        if (condition())
            testIf();
        else
            testIf();
        if (condition())
            testIf();
        else {
            testIf();
        }
        if (condition()) {
            testIf();
        }
        else
            testIf();
        if (condition())
            if (condition())
                testIf();
    }

    void whitespaceAfterSemi()
    {
        //reject
        int i = 1;int j = 2;

        //accept
        for (;;) {
        }
    }

    /** Empty constructor block. **/
    public InputWhitespaceAroundBraces2() {}

    /** Empty method block. **/
    public void emptyImplementation() {}

    public void foo() {
        boolean flag = true;
        if(flag){} // 4 violations
    }
}
