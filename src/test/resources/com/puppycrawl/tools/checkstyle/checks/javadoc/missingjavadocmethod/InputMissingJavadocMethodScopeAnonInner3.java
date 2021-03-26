////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2021
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;

/* Config:
 * scope = private
 */
/**
 * Tests for anonymous inner types
 */
public class InputMissingJavadocMethodScopeAnonInner3
{
    /**
     * button.
     */
    private JButton mButton = new JButton();

    /**
     * anon inner in member variable initialization.
     */
    private Runnable mRunnable = new Runnable() { // ok
        public void run() // ok
        {
            System.identityHashCode("running");
        }
    };

    /**
     * anon inner in constructor.
     */
    InputMissingJavadocMethodScopeAnonInner3() // ok
    {
        mButton.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked( MouseEvent aEv ) // ok
            {
                System.identityHashCode("click");
            }
        } );
    }

    /**
     * anon inner in method
     */
    public void addInputAnonInner() // ok
    {
        mButton.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked( MouseEvent aEv ) // ok
            {
                System.identityHashCode("click");
            }
        } );
    }
}
