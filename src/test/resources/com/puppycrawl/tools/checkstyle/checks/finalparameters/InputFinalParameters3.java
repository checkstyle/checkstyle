/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Test case for detecting missing final parameters.
 * @author Lars Kühne
 **/
class InputFinalParameters3
{
    /** no param constructor */
    InputFinalParameters3()
    {
    }

    /** non final param constructor */
    InputFinalParameters3(String s)
    {
    }

    /** non final param constructor */
    InputFinalParameters3(final Integer i)
    {
    }

    /** final param constructor with annotation */
    InputFinalParameters3(final @MyAnnotation33 Class<Object> i)
    {
    }

    /** non-final param constructor with annotation*/
    InputFinalParameters3(@MyAnnotation33 Boolean i)
    {
    }

    /** mixed */
    InputFinalParameters3(String s, final Integer i)
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
}

@interface MyAnnotation33 {
}
