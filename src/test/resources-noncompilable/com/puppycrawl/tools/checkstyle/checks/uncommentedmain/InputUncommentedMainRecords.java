/*
UncommentedMain
excludedClasses = (default)^$


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

public record InputUncommentedMainRecords(Integer x) {
    public static void main(String... args) { // violation
        System.out.println("no comments here!");
    }

    // public static void main(String... args) { } // ok

    // unlike inner classes, inner records CAN have static methods
    record InnerRecord(String... args) {
        public static void main(String... args) { // violation
            System.out.println("no comments here!");
        }

        record InnerRecordCeption(int y, int x) {
            public static void main(String... args) { // violation
                System.out.println("no comments here either!");
            }
        }
    }
}
