package com.openjdk.checkstyle.test.chapterprogrammingpractices.ruleprogrammingpractices;

// violation first line 'Header mismatch'

public class InputProgrammingPracticesOverrideTwo {

    record Person(String name, int age) {

        // violation below, 'method must include @java.lang.Override annotation.'
        public String name() {
            return name.toUpperCase();
        }

        public String name(String value) {
            return value;
        }

        @Override
        public int age() {
            return age;
        }

        public int age(int value) {
            return value;
        }
    }
}
