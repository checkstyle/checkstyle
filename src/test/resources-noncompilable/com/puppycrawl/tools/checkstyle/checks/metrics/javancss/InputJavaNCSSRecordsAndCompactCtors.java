/*
JavaNCSS
methodMaximum = 7
classMaximum = 3
fileMaximum = 2
recordMaximum = 4


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.metrics.javancss;  // violation

import java.time.LocalDateTime;

public class InputJavaNCSSRecordsAndCompactCtors {  // violation

    class TestClass {  // violation
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

    record MyRecord1(boolean t, boolean f) {  // violation
        public MyRecord1 {
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
        } // 6
    }

    record MyRecord2(boolean a, boolean b) {  // violation
        MyRecord2() {
            this(true, false);
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");
            System.out.println("test");

        } // 6

        //should give an ncss of 8 , violation for method
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
    } // 15

    record MyRecord3(boolean a, boolean b) {  // violation
        public void foo () {
            //should give an ncss of 2
            record TestInnerRecord() {

                private static Object test;
            }
            System.out.println("test");
            System.out.println("test");
        }
    } // 6

    record MyRecord4(int x, int y) { // ok
        //should give an ncss of 2
        record TestInnerRecord() {
            private static Object test;
        }
    }

    record MyRecord5(int x, int y) { // ok
        //should give an ncss of 4
        public MyRecord5{
            if(x > 5) {
                System.out.println("x greater than 5!");
            }
        }
    }

    record MyRecord6(int x, int y) { // ok
        //should give an ncss of 2
        public MyRecord6{
        }
    }

    public record FXOrder(int units,  // violation
                          String side,
                          double price,
                          LocalDateTime sentAt,
                          int ttl) {
        public FXOrder {
            if (units < 1) {
                throw new IllegalArgumentException(
                        "FXOrder units must be positive");
            }
            if (ttl < 0) {
                throw new IllegalArgumentException(
                        "FXOrder TTL must be positive, or 0 for market orders");
            }
            if (price <= 0.0) {
                throw new IllegalArgumentException(
                        "FXOrder price must be positive");
            }
        } // 8
    }

    public class FXOrderClass {  // violation
        private int units;
        private int ttl;
        private double price; // 3

        public FXOrderClass(int units, int ttl, double price) {
            if (units < 1) {
                throw new IllegalArgumentException(
                        "FXOrder units must be positive");
            }
            if (ttl < 0) {
                throw new IllegalArgumentException(
                        "FXOrder TTL must be positive, or 0 for market orders");
            }
            if (price <= 0.0) {
                throw new IllegalArgumentException(
                        "FXOrder price must be positive");
            }
        } // 8
    }

    record MyRecord7(int x, int y) {  // violation
        public MyRecord7{ // violation
            System.out.println("test");
            System.out.println("test");
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

    class MyClass {  // violation
        MyClass(int x) {  // violation
            System.out.println("test");
            System.out.println("test");
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

