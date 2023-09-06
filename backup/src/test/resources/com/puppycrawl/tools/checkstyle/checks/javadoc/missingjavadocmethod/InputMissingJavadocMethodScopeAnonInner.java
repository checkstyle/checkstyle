/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;

/**
 * Tests for anonymous inner types
 */
public class InputMissingJavadocMethodScopeAnonInner
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
    InputMissingJavadocMethodScopeAnonInner() // ok
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
