package com.openjdk.checkstyle.test.chapterformatting.ruleorderofconstructorsandoverloadedmethods;

public class InputOrderOfConstructorsAndOverloadedMethodsDoAndDonts {

    class Do {
        Do() {
            this(10);
        }

        Do(int capacity) {
            this(capacity, 10.0);
        }

        Do(int capacity, double loadFactor) {
        }
    }

    class Donts {

        void logValue(int i) {
            System.out.println(i);
        }

        void setValue(int i) {
            System.out.println(i);
        }

        // violation 2 lines below """All overloaded methods should be placed next
        //  to each other. Previous overloaded method located at line '20'."""
        void logValue(double d) {
            System.out.println(d);
        }

        // violation 2 lines below """All overloaded methods should be placed next
        //  to each other. Previous overloaded method located at line '24'."""
        void setValue(double d) {
            System.out.println(d);
        }
    }

}
