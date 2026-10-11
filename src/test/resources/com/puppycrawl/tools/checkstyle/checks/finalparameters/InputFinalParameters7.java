/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

/**
 * Test case for detecting missing final parameters.
 **/
class InputFinalParameters7
{
    /** methods with complicated types of the parameters. */
    void methodA(String aParam) {
    }

    void methodB(String[] args) {
    }

    void methodC(String[] args) {
    }

    /** some catch blocks */
    void method1()
    {
        try {
            String.CASE_INSENSITIVE_ORDER.equals("");
        }
        catch (NullPointerException npe) {
            npe.getMessage();
        }
        catch (@MyAnnotation32 final ClassCastException e) {
            e.getMessage();
        }
        catch (RuntimeException e) {
            e.getMessage();
        }
        catch (@MyAnnotation32 NoClassDefFoundError e) {
            e.getMessage();
        }
    }

    native void method(int i);
}

abstract class AbstractClass7
{
    public abstract void abstractMethod(int aParam);
}

class Foo7
{
    /* Some for-each clauses */
    public void Bar()
    {
        for(String s : someExpression())
        {

        }
        for(final String s : someExpression())
        {

        }
        for(@MyAnnotation32 String s : someExpression())
        {

        }
        for(@MyAnnotation32 final String s : someExpression())
        {

        }
    }

    private String[] someExpression()
    {
        return null;
    }
}

@interface MyAnnotation32 {
}
