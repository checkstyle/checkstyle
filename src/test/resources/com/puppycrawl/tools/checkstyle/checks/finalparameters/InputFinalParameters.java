/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.ActionEvent;

/**
 * Test case for detecting missing final parameters.
 * @author Lars Kühne
 **/
class InputFinalParameters
{
    /** no param constructor */
    InputFinalParameters()
    {
    }

    /** non final param constructor */
    InputFinalParameters(String s) // violation, 's' should be final
    {
    }

    /** non final param constructor */
    InputFinalParameters(final Integer i)
    {
    }

    /** final param constructor with annotation */
    InputFinalParameters(final @MyAnnotation33 Class<Object> i)
    {
    }

    /** non-final param constructor with annotation*/
    InputFinalParameters(@MyAnnotation33 Boolean i) // violation, 'i' should be final
    {
    }

    /** mixed */
    InputFinalParameters(String s, final Integer i) // violation, 's' should be final
    {
    }

    /** no param method */
    void method()
    {
    }

    /** non final param method */
    void method(String s) // violation, 's' should be final
    {
    }

    /** final param method */
    void method(final Integer i)
    {
    }

    /** final param method with annotation **/
    void method(@MyAnnotation33 final Object s)
    {

    }

    /** non-final param method with annotation **/
    void method(@MyAnnotation33 Class<Object> s) // violation, 's' should be final
    {

    }

    /** mixed */
    void method(String s, final Integer i) // violation, 's' should be final
    {
    }

    /** interface methods should not be flagged. */
    interface TestInterface
    {
        void method(String s);
    }

    /** methods in anonymous inner classes */
    void holder()
    {
        Action a = new AbstractAction()
            {
                public void actionPerformed(ActionEvent e) // violation, 'e' should be final
                {
                }
                void somethingElse(@MyAnnotation33 ActionEvent e) // violation, 'e' should be final
                {
                }
            };

        Action b = new AbstractAction()
            {
                public void actionPerformed(final ActionEvent e)
                {
                }
                void somethingElse(@MyAnnotation33 final ActionEvent e)
                {
                }
            };
    }

    /** methods with complicated types of the parameters. */
    void methodA(java.lang.String aParam) { // violation, 'aParam' should be final
    }

    void methodB(String[] args) { // violation, 'args' should be final
    }

    void methodC(java.lang.String[] args) { // violation, 'args' should be final
    }

    /** some catch blocks */
    void method1()
    {
        try {
            String.CASE_INSENSITIVE_ORDER.equals("");
        }
        catch (java.lang.NullPointerException npe) {
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

abstract class AbstractClass
{
    public abstract void abstractMethod(int aParam);
}

class Foo
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

@interface MyAnnotation3 {
}
