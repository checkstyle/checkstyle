/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = FOR_EACH_CLAUSE


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

/**
 * Test case for detecting missing final parameters.
 * @author Lars Kühne
 **/
class InputFinalParameters5
{
}

class Foo5
{
    /* Some for-each clauses */
    public void Bar()
    {
        for(String s : someExpression()) // violation, 's' should be final
        {

        }
        for(final String s : someExpression())
        {

        }
        for(@MyAnnotation33 String s : someExpression()) // violation, 's' should be final
        {

        }
        for(@MyAnnotation33 final String s : someExpression())
        {

        }
    }

    private String[] someExpression()
    {
        return null;
    }
}

