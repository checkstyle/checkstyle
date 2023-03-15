/*
JavaNCSS
methodMaximum = 0
classMaximum = 1
fileMaximum = 2
recordMaximum = (default)150


*/

// should give an ncss of 35
package com.puppycrawl.tools.checkstyle.checks.metrics.javancss; // violation

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


//should give an ncss of 22
public class InputJavaNCSS { // violation

    private Object mObject;

    //should count as 2
    private void testMethod1() { // violation

        //should count as 1
        int x = 1, y = 2;
    }

    //should count as 4
    private void testMethod2() { // violation

        int abc = 0;

        //should count as 2
        testLabel: abc = 1;
    }

    //should give an ncss of 12
    private void testMethod3() { // violation

        int a = 0;
        switch (a) {
            case 1: //falls through
            case 2: System.identityHashCode("Hello"); break;
            default: break;
        }

        ItemListener lis = new ItemListener() {

            //should give an ncss of 2
            public void itemStateChanged(ItemEvent e) { // violation
                System.identityHashCode("Hello");
            }
        };
    }

    //should give an ncss of 2
    private class TestInnerClass { // violation

        private Object test;
    }
}

//should give an ncss of 10
class TestTopLevelNestedClass { // violation

    private Object mObject;

    //should give an ncss of 8
    private void testMethod() { // violation

        for (int i=0; i<10; i++) {

            if (i==0) {

                //should count as 1
                int x = 1, y = 2;
            }
            else {
                int abc = 0;

                //should count as 2
                testLabel: abc = 1;
            }
        }
    }
}

class Input0 { // violation
    static { } // violation
    { } // violation
    public Input0() { } // violation
}
