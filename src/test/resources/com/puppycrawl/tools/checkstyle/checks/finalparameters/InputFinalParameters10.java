/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = FOR_EACH_CLAUSE


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

/**
 * Test case for detecting missing final parameters.
 **/
class InputFinalParameters10
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

abstract class AbstractClass10
{
    public abstract void abstractMethod(int aParam);
}

class Foo10
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
