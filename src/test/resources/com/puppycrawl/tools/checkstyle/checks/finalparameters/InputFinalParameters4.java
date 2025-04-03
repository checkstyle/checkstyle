/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = LITERAL_CATCH


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Test case for detecting missing final parameters.
 * @author Lars Kühne
 **/
class InputFinalParameters4
{
    /** no param constructor */
    InputFinalParameters4()
    {
    }

    /** non final param constructor */
    InputFinalParameters4(String s)
    {
    }

    /** non final param constructor */
    InputFinalParameters4(final Integer i)
    {
    }

    /** final param constructor with annotation */
    InputFinalParameters4(final @MyAnnotation33 Class<Object> i)
    {
    }

    /** non-final param constructor with annotation*/
    InputFinalParameters4(@MyAnnotation33 Boolean i)
    {
    }

    /** mixed */
    InputFinalParameters4(String s, final Integer i)
    {
    }

    /** no param method */
    void method()
    {
    }

    /** non final param method */
    void method(String s)
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
    void method(@MyAnnotation33 Class<Object> s)
    {

    }

    /** mixed */
    void method(String s, final Integer i)
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
                public void actionPerformed(ActionEvent e)
                {
                }
                void somethingElse(@MyAnnotation33 ActionEvent e)
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
        catch (NullPointerException npe) { // violation, 'npe' should be fina;
            npe.getMessage();
        }
        catch (@MyAnnotation33 final ClassCastException e) {
            e.getMessage();
        }
        catch (RuntimeException e) { // violation, 'e' should be final
            e.getMessage();
        }
        catch (@MyAnnotation33 NoClassDefFoundError e) { // violation, 'e' should be final
            e.getMessage();
        }
    }

    native void method(int i);
}

abstract class AbstractClass4
{
    public abstract void abstractMethod(int aParam);
}

class Foo4
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

@interface MyAnnotation34 {
}
