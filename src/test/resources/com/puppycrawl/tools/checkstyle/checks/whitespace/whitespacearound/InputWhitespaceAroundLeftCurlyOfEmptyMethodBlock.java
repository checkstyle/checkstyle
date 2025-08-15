/*
WhitespaceAround
allowEmptyConstructors = true
allowEmptyMethods = true
allowEmptyTypes =true
allowEmptyLoops = true
allowEmptyLambdas = true
allowEmptyCatches = (default)false
allowEmptySwitchBlockStatements = true
ignoreEnhancedForColon = false
tokens = (default)ASSIGN, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR, \
         BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, LAMBDA, LAND, \
         LCURLY, LE, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, \
         LITERAL_FOR, LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, \
         LITERAL_TRY, LITERAL_WHILE, LOR, LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN, \
         NOT_EQUAL, PLUS, PLUS_ASSIGN, QUESTION, RCURLY, SL, SLIST, SL_ASSIGN, SR, \
         SR_ASSIGN, STAR, STAR_ASSIGN, LITERAL_ASSERT, TYPE_EXTENSION_AND


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

/** some javadoc. */
public class InputWhitespaceAroundLeftCurlyOfEmptyMethodBlock {

    InputWhitespaceAroundLeftCurlyOfEmptyMethodBlock instance =
        new InputWhitespaceAroundLeftCurlyOfEmptyMethodBlock(){}; // ok until #10834

    // violation below ''{' is not preceded with whitespace'
    InputWhitespaceAroundLeftCurlyOfEmptyMethodBlock(){}

    void method1(int k) {
        {}
        // 2 violations above:
        //   ''{' is not followed by whitespace.'
        //   ''}' is not preceded with whitespace.'
    }

    void method5(int k) {
        if (k > 5) {
            int i = 1;
        }}
    // 2 violations above:
    //   ''}' is not followed by whitespace'
    //   ''}' is not preceded with whitespace'

    void method2(){} // violation ''{' is not preceded with whitespace'

    /** some javadoc. */
    public void method3(){} // violation ''{' is not preceded with whitespace'

    /** some javadoc. */
    public static void foo(int val) { value = val;}
    // violation above ''}' is not preceded with whitespace'

    class Inner { public static boolean foo2() { return "😂 ".equals("da👉🏻");}
        // violation above ''}' is not preceded with whitespace'
    }

}
