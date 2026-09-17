/*
WhitespaceAround
allowEmptyConstructors = (default)false
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
allowEmptySwitchBlockStatements = (default)false
ignoreEnhancedForColon = (default)true
tokens = (default)ASSIGN, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR, \
         BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, LAMBDA, LAND, \
         LCURLY, LE, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, \
         LITERAL_FOR, LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, \
         LITERAL_TRY, LITERAL_WHILE, LOR, LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN, \
         NOT_EQUAL, PLUS, PLUS_ASSIGN, QUESTION, RCURLY, SL, SLIST, SL_ASSIGN, SR, \
         SR_ASSIGN, STAR, STAR_ASSIGN, LITERAL_ASSERT, TYPE_EXTENSION_AND, LITERAL_WHEN


*/

package com . puppycrawl
    .tools.
    checkstyle.checks.whitespace.whitespacearound;

/**
 * Class for testing whitespace issues.
 * violation missing author tag
 **/
class InputWhitespaceAroundKeywordsAndOperators
{
    /** ignore assignment **/
    private int mVar1=1;
    // 2 violations above:
    // ''=' is not followed by whitespace.'
    // ''=' is not preceded with whitespace.'
    /** ignore assignment **/
    private int mVar2 =1; // violation ''=' is not followed by whitespace'
    /** Should be ok **/
    private int mVar3 = 1;

    /** method **/
    void method1()
    {
        final int a = 1;
        int b= 1; // violation ''=' is not preceded with whitespace'
        b=1;
        // 2 violations above:
        // ''=' is not followed by whitespace.'
        // ''=' is not preceded with whitespace.'
        b+=1;
        // 2 violations above:
        // 'is not followed by whitespace.'
        // 'is not preceded with whitespace.'
        b -=- 1 + (+ b); // violation ''-=' is not followed by whitespace'
        b = b ++ + b --; // Ignore 1
        b = ++ b - -- b; // Ignore 1
    }

    /** method **/
    void method2()
    {
        synchronized(this) { // violation ''synchronized' is not followed by whitespace'
        }
        try{
            // 2 violations above:
            // ''try' is not followed by whitespace.'
            // ''{' is not preceded with whitespace.'
        }
        catch(RuntimeException e){
            // 2 violations above:
            // ''catch' is not followed by whitespace.'
            // ''{' is not preceded with whitespace.'
        }
    }

    /**
       skip blank lines between comment and code,
       should be ok
    **/


    private int mVar4 = 1;


    /** test WS after void return */
    private void fastExit()
    {
        boolean complicatedStuffNeeded = true;
        if( !complicatedStuffNeeded ) // violation ''if' is not followed by whitespace'
        {
            return; // should not complain about missing WS after return
        }
        else
        {
            // do complicated stuff
        }
    }


    /** test WS after non void return
     @return 2
    */
    private int nonVoid()
    {
        if ( true )
        {
            return(2); // violation ''return' is not followed by whitespace'
        }
        else
        {
            return 2;
        }
    }
}
