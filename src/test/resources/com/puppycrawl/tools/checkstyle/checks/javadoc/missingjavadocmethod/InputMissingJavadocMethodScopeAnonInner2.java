/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = anoninner
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
public class InputMissingJavadocMethodScopeAnonInner2
{
    /**
     * button.
     */
    private JButton mButton = new JButton();

    /**
     * anon inner in member variable initialization.
     */
    private Runnable mRunnable = new Runnable() {
        public void run() // violation
        {
            System.identityHashCode("running");
        }
    };

    /**
     * anon inner in constructor.
     */
    InputMissingJavadocMethodScopeAnonInner2()
    {
        mButton.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked( MouseEvent aEv ) // violation
            {
                System.identityHashCode("click");
            }
        } );
    }

    /**
     * anon inner in method
     */
    public void addInputAnonInner()
    {
        mButton.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked( MouseEvent aEv ) // violation
            {
                System.identityHashCode("click");
            }
        } );
    }
}
