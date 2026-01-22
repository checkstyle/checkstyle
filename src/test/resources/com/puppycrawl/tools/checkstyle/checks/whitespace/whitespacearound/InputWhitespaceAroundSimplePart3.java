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

public class InputWhitespaceAroundSimplePart3
{

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
class InputWhitespaceAroundSimple2
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
enum MyEnum1
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}
