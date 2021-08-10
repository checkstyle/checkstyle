/*
AnonInnerLength
max = 6


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.anoninnerlength;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

/**
 * Tests for length of anonymous inner types
 * @author Rob Worth
 * @author Lars Kühne
 **/
public class InputAnonInnerLength2
{
    /**
     * Check that instantiations of normal classes work OK.
     */
    private JButton mButton = new JButton();

    private class MyInner
    {
        private MyInner(int[] anArray)
        {
        }
    }

    /**
     * the AnonInnerLengthCheck works with 'new' and RCURLY - check that
     * it will not confuse constructors calls with array params with
     * anon inners.
     */
    private MyInner myInner = new MyInner(new int[]{
            // make the array span multiple lines
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            }
    );

    /**
       anon inner in member variable initialization which is 21 lines long
    */
    private Runnable mRunnable1 = new Runnable() { // violation
        public void run() // should not have to be documented, class is anon.
        {
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
        }
    };

    /**
       anon inner in member variable initialization which is 20 lines long
    */
    private Runnable mRunnable2 = new Runnable() { // violation
        public void run() // should not have to be documented, class is anon.
        {
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
            System.identityHashCode("running");
        }
    };

    /**
       anon inner in constructor.
    */
    InputAnonInnerLength2()
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
