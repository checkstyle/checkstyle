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

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

/**
 * Input for testing whitespace around operators.
 */
class InputWhitespaceAroundOperators
{
    /** test casts **/
    private void testCasts()
    {
        Object o = (Object) new Object();
        o = (Object)o;
        o = ( Object ) o;
        o = (Object)
            o;
    }

    /** test questions **/
    private void testQuestions()
    {
        boolean b = (1 == 2)?true:false;
        // 4 violations above:
        //                      ''?' is not followed by whitespace'
        //                      ''?' is not preceded with whitespace'
        //                           '':' is not followed by whitespace'
        //                           '':' is not preceded with whitespace'
        b = (1==2) ? false : true;
        // 2 violations above:
        //      ''==' is not followed by whitespace'
        //      ''==' is not preceded with whitespace'
    }

    /** star test **/
    private void starTest()
    {
        int x = 2 *3* 4;
        // 2 violations above:
        //          ''*' is not followed by whitespace'
        //            ''*' is not preceded with whitespace'
    }

    /** boolean test **/
    private void boolTest()
    {
        boolean a = true;
        boolean x = ! a;
        int z = ~1 + ~ 2;
    }

    /** division test **/
    private void divTest()
    {
        int a = 4 % 2;
        int b = 4% 2; // violation ''%' is not preceded with whitespace'
        int c = 4 %2; // violation ''%' is not followed by whitespace'
        int d = 4%2;
        // 2 violations above:
        //         ''%' is not followed by whitespace'
        //         ''%' is not preceded with whitespace'
        int e = 4 / 2;
        int f = 4/ 2; // violation ''/' is not preceded with whitespace'
        int g = 4 /2; // violation ''/' is not followed by whitespace'
        int h = 4/2;
        // 2 violations above:
        //         ''/' is not followed by whitespace'
        //         ''/' is not preceded with whitespace'
    }

    /** @return dot test **/
    private java .lang.  String dotTest()
    {
        Object o = new java.lang.Object();
        o.
            toString();
        o
            .toString();
        o . toString();
        return o.toString();
    }

    /** assert statement test */
    public void assertTest()
    {
        assert true;

        assert true : "Whups";

        // evil colons, should be OK
        assert "OK".equals(null) ? false : true : "Whups";

        // missing WS around assert
        assert(true); // violation ''assert' is not followed by whitespace'

        // missing WS around colon
        assert true:"Whups";
        // 2 violations above:
        //           '':' is not followed by whitespace'
        //           '':' is not preceded with whitespace'
    }
}
