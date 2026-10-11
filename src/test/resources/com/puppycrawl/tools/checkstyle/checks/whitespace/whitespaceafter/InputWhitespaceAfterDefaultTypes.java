/*
WhitespaceAfter
tokens = (default)COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, \
         LITERAL_DO, LITERAL_FOR, LITERAL_FINALLY, LITERAL_RETURN, LITERAL_YIELD, \
         LITERAL_CATCH, DO_WHILE, ELLIPSIS, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, \
         LITERAL_TRY, LITERAL_CASE, LAMBDA, LITERAL_WHEN

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;


/** Test class for variable naming in for each clause. */
class InputWhitespaceAfterDefaultTypes
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
