////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;

/**
 * Tests for anonymous inner types
 * @author Lars Kühne
 **/
public class InputMissingJavadocMethodScopeAnonInner
{
    /**
       button.
    */
    private JButton mButton = new JButton();

    /**
       anon inner in member variable initialization.
    */
    private Runnable mRunnable = new Runnable() {
        public void run() // should not have to be documented, class is anon.
        {
            System.identityHashCode("running");
        }
    };

    /**
       anon inner in constructor.
    */
    InputMissingJavadocMethodScopeAnonInner()
    {
        mButton.addMouseListener( new MouseAdapter()
            {
                public void mouseClicked( MouseEvent aEv )
                {
                    System.identityHashCode("click");
                }
            } );
    }

    /**
       anon inner in method
    */
    public void addInputAnonInner()
    {
        mButton.addMouseListener( new MouseAdapter()
            {
                public void mouseClicked( MouseEvent aEv )
                {
                    System.identityHashCode("click");
                }
            } );
    }
}
