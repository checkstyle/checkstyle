////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2003
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

import javax.swing.AbstractAction;
import java.awt.event.Actionevent;

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

    /** non final param method */
    void method(final Integer i)
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
                void actionPerformed(ActionEvent e)
                {
                }
            };

        Action b = new AbstractAction()
            {
                void actionPerformed(final ActionEvent e)
                {
                }
            };
    }
}
