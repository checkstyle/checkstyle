/*
AnonInnerLength
max = (default)20


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.anoninnerlength;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;

public class InputAnonInnerLengthPartTwo {

    private JButton mButton = new JButton();

    /**
     anon inner in member variable initialization which is 21 lines long
     */
    private Runnable mRunnable1 = new Runnable() { // violation
        public void run()
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
     anon inner in constructor.
     */
    InputAnonInnerLengthPartTwo()
    {
        mButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent aEv)
            {
                System.identityHashCode("click");
            }
        });
    }

    /**
     anon inner in method.
     */
    public void addInputAnonInner()
    {
        mButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent aEv)
            {
                System.identityHashCode("click");
            }
        });
    }

}
