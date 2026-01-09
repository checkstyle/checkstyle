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

/**
 * Contains simple mistakes:
 * - Long lines
 * - Tabs
 * - Format of variables and parameters
 * - Order of modifiers
 * @author Oliver Burn
 **/
final class InputWhitespaceAroundSimplePart1 {
    // Long line ----------------------------------------------------------------
    // Contains a tab ->    <-
    // Contains trailing whitespace ->

    // Name format tests
    //
    /**
     * Invalid format
     **/
    public static final int badConstant = 2;
    /**
     * Valid format
     **/
    public static final int MAX_ROWS = 2;

    /**
     * Invalid format
     **/
    private static int badStatic = 2;
    /**
     * Valid format
     **/
    private static int sNumCreated = 0;

    /**
     * Invalid format
     **/
    private int badMember = 2;
    /**
     * Valid format
     **/
    private int mNumCreated1 = 0;
    /**
     * Valid format
     **/
    protected int mNumCreated2 = 0;

    /**
     * commas are wrong
     **/
    private int[] mInts = new int[]{1, 2, 3,
            4};

    //
    // Accessor tests
    //
    /**
     * should be private
     **/
    public static int sTest1;
    /**
     * should be private
     **/
    protected static int sTest3;
    /**
     * should be private
     **/
    static int sTest2;

    /**
     * should be private
     **/
    int mTest1;
    /**
     * should be private
     **/
    public int mTest2;

    //
    // Parameter name format tests
    //

    /**
     * @param badFormat1 bad format
     * @param badFormat2 bad format
     * @param badFormat3 bad format
     * @return hack
     * @throws Exception abc
     **/
    int test1(int badFormat1, int badFormat2,
              final int badFormat3)
            throws Exception {
        return 0;
    }

}
