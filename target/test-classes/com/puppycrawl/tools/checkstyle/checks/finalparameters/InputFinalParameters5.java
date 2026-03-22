/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = FOR_EACH_CLAUSE


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Test case for detecting missing final parameters.
 * @author Lars Kühne
 **/
class InputFinalParameters5
{
    /** no param constructor */
    InputFinalParameters5()
    {
    }

    /** non final param constructor */
    InputFinalParameters5(String s)
    {
    }

    /** non final param constructor */
    InputFinalParameters5(final Integer i)
    {
    }

    /** final param constructor with annotation */
    InputFinalParameters5(final @MyAnnotation33 Class<Object> i)
    {
    }

    /** non-final param constructor with annotation*/
    InputFinalParameters5(@MyAnnotation33 Boolean i)
    {
    }

    /** mixed */
    InputFinalParameters5(String s, final Integer i)
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

abstract class AbstractClass5
{
    public abstract void abstractMethod(int aParam);
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

@interface MyAnnotation35 {
}
