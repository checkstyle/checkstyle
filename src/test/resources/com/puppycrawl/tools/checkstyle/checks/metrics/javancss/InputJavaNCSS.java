/*
JavaNCSS
methodMaximum = 0
classMaximum = 1
fileMaximum = 2
recordMaximum = (default)150


*/

// should give an ncss of 35
package com.puppycrawl.tools.checkstyle.checks.metrics.javancss; // violation 'NCSS for this file is 39 (max allowed is 2).'

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


//should give an ncss of 22
public class InputJavaNCSS { // violation 'NCSS for this class is 22 (max allowed is 1).'

  private Object mObject;

  //should count as 2
  private void testMethod1() { // violation 'NCSS for this method is 2 (max allowed is 0).'

    //should count as 1
    int x = 1, y = 2;
  }

  //should count as 4
  private void testMethod2() { // violation 'NCSS for this method is 4 (max allowed is 0).'

    int abc = 0;

    //should count as 2
    testLabel: abc = 1;
  }

  //should give an ncss of 12
  private void testMethod3() { // violation 'NCSS for this method is 12 (max allowed is 0).'

    int a = 0;
    switch (a) {
        case 1: //falls through
        case 2: System.identityHashCode("Hello"); break;
        default: break;
    }

    ItemListener lis = new ItemListener() {

      //should give an ncss of 2
      public void itemStateChanged(ItemEvent e) { // violation '.* is 2 (max allowed is 0).'
          System.identityHashCode("Hello");
      }
    };
  }

  //should give an ncss of 2
  private class TestInnerClass { // violation 'NCSS for this class is 2 (max allowed is 1).'

      private Object test;
  }
}

//should give an ncss of 10
class TestTopLevelNestedClass { // violation 'NCSS for this class is 10 (max allowed is 1).'

  private Object mObject;

  //should give an ncss of 8
  private void testMethod() { // violation 'NCSS for this method is 8 (max allowed is 0).'

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

class Input0 { // violation 'NCSS for this class is 4 (max allowed is 1).'
  static { } // violation 'NCSS for this method is 1 (max allowed is 0).'
  { } // violation 'NCSS for this method is 1 (max allowed is 0).'
  public Input0() { } // violation 'NCSS for this method is 1 (max allowed is 0).'
}
