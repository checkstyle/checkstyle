////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2003
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

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
    InputFinalParameters(String s)
    {
    }

    /** non final param constructor */
    InputFinalParameters(final Integer i)
    {
    }

    /** final param constructor with annotation */
    InputFinalParameters(final @MyAnnotation Class i)
    {
    }

    /** non-final param constructor with annotation*/
    InputFinalParameters(@MyAnnotation Boolean i)
    {
    }

    /** mixed */
    InputFinalParameters(String s, final Integer i)
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
    void method(@MyAnnotation final Object s)
    {

    }

    /** non-final param method with annotation **/
    void method(@MyAnnotation Class s)
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
                void somethingElse(@MyAnnotation ActionEvent e)
                {
                }
            };

        Action b = new AbstractAction()
            {
                public void actionPerformed(final ActionEvent e)
                {
                }
                void somethingElse(@MyAnnotation final ActionEvent e)
                {
                }
            };
    }

    /** methods with complicated types of the parameters. */
    void methodA(java.lang.String aParam) {
    }

    void methodB(String[] args) {
    }

    void methodC(java.lang.String[] args) {
    }

    /** some catch blocks */
    void method1()
    {
        try {
            System.err.println("");
        }
        catch (java.lang.NullPointerException npe) {
            npe.printStackTrace();
        }
        catch (@MyAnnotation final ClassCastException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        catch (@MyAnnotation NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }
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
        for(@MyAnnotation String s : someExpression())
        {

        }
        for(@MyAnnotation final String s : someExpression())
        {

        }
    }

    private String[] someExpression()
    {
        return null;
    }
}

@interface MyAnnotation {
}
