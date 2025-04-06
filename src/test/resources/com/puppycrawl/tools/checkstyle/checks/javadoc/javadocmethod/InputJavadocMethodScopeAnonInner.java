/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;

public class InputJavadocMethodScopeAnonInner
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
    InputJavadocMethodScopeAnonInner()
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
