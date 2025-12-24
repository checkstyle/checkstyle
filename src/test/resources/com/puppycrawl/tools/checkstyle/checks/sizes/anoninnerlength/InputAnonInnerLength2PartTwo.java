/*
AnonInnerLength
max = 6


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.anoninnerlength;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class InputAnonInnerLength2PartTwo {

    private JButton mButton = new JButton();

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
    InputAnonInnerLength2PartTwo()
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
    public void addInputAnonInnerPartTwo()
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
