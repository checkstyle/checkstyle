/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

/**
 * Test case for detecting missing final parameters.
 **/
class InputFinalParameters8
{
    /** methods with complicated types of the parameters. */
    void methodA(String aParam) { // violation, 'aParam' should be final
    }

    void methodB(String[] args) { // violation, 'args' should be final
    }

    void methodC(String[] args) { // violation, 'args' should be final
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
        catch (@MyAnnotation33 final ClassCastException e) {
            e.getMessage();
        }
        catch (RuntimeException e) {
            e.getMessage();
        }
        catch (@MyAnnotation33 NoClassDefFoundError e) {
            e.getMessage();
        }
    }

    native void method(int i);
}

abstract class AbstractClass8
{
    public abstract void abstractMethod(int aParam);
}

class Foo8
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
        for(@MyAnnotation33 String s : someExpression())
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
