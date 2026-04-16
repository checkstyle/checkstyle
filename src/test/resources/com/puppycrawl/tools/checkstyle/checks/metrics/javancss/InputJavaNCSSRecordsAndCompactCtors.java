/*
JavaNCSS
methodMaximum = 7
classMaximum = 3
fileMaximum = 2
recordMaximum = 4


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.metrics.javancss;  // violation 'NCSS for this file is 55 (max allowed is 2).'

public class InputJavaNCSSRecordsAndCompactCtors {  // violation '.* is 54 (max allowed is 3).'

  class TestClass {  // violation 'NCSS for this class is 7 (max allowed is 3).'
    //should count as 2
    private void testMethod1() {

      //should count as 1
      int x = 1, y = 2;
    }

    //should count as 4
    private void testMethod2() {

      int abc = 0;

      //should count as 2
      testLabel: abc = 1;
    } // 7
  }

  record MyRecord1(boolean t, boolean f) {  // violation '.* is 6 (max allowed is 4).'
    public MyRecord1 {
      System.out.println("test");
      System.out.println("test");
      System.out.println("test");
      System.out.println("test");
    } // 6
  }

  record MyRecord2(boolean a, boolean b) {  // violation '.* is 15 (max allowed is 4).'
    MyRecord2() {
      this(true, false);
      System.out.println("test");
      System.out.println("test");
      System.out.println("test");
      System.out.println("test");

    } // 6

    //should give an ncss of 8 , violation for method
    private void testMethod() { // violation 'NCSS for this method is 8 (max allowed is 7).'

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
  } // 15

  record MyRecord3(boolean a, boolean b) {  // violation '.* is 6 (max allowed is 4).'
    public void foo () {
      //should give an ncss of 2
      record TestInnerRecord() {

        private static Object test;
      }
      System.out.println("test");
      System.out.println("test");
    }
  } // 6

  record MyRecord4(int x, int y) {
    //should give an ncss of 2
    record TestInnerRecord() {
      private static Object test;
    }
  }

  record MyRecord5(int x, int y) {
    //should give an ncss of 4
    public MyRecord5{
      if(x > 5) {
        System.out.println("x greater than 5!");
      }
    }
  }

  record MyRecord6(int x, int y) {
    public MyRecord6{
    }
  }

  record MyRecord7(int x, int y) {  // violation 'NCSS for this record is 10 (max allowed is 4).'
    public MyRecord7{ // violation 'NCSS for this method is 9 (max allowed is 7).'
      System.out.println("test");
      System.out.println("test");
      System.out.println("test");
      System.out.println("test");
      System.out.println("test");
      System.out.println("test");
      System.out.println("test");
      System.out.println("test");
    }
  }

}

