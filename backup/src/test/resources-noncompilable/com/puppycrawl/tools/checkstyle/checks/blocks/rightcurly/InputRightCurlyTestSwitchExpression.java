/*
RightCurly
option = ALONE
tokens = LITERAL_SWITCH, LITERAL_IF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestSwitchExpression {

    public void test() {
        int day = 3;
        String check =
            switch (day) {
                case 1 -> "Monday";
                case 2 -> "Tuesday";
                case 3 -> "Wednesday";
                default -> "Invalid day";
            }; } // ok

    public void test1() {
        int day = 3;
        String check =
            switch (day) {
                case 1 -> "Monday";
                default -> "Invalid day";
            }; // ok
    }

    public void test2() {
        int day = 3;
        String check =
            switch (day) {
                case 1 -> "Monday";
                default -> "Invalid day";
            }; System.out.println(check); // ok
    }

    public void test3() {
        int num = 2;

        switch (num) {
            case 1 -> System.out.println("One");
            default -> System.out.println("Other");
    } int a; } // violation ''}' at column 5 should be alone on a line'

    public void test4() {
        int num = 2;

        switch (num) {
            case 1 -> System.out.println("One");
            default -> System.out.println("Other");
    } } // violation ''}' at column 5 should be alone on a line'

    public void test5() {
        int num = 0;
        switch (num) {
            case 1 -> System.out.println("One");
            default -> System.out.println("Other");
        } // ok
    }

    public void test6() {
        int num = 2;

        switch (num) {
            //no case or default statement
        } // ok
    }

    public void test7() {
        int num = 2;

        switch (num) {
            //no case or default statement
        } // ok
    }

    public void test8() {

        int expression = 2;
        switch (expression) {
            case 1 -> {
                Runnable result1 = () -> System.out.println("result1");
            }
            default -> {
                Runnable defaultResult = () -> System.out.println("defaultResult");
            }
        } int a; // violation ''}' at column 9 should be alone on a line'
    }

    public void test9() {

        int expression = 2;
        switch (expression) {
            case 1 -> {
                Runnable result1 = () -> System.out.println("result1");
            }
            default -> {
                Runnable defaultResult = () -> System.out.println("defaultResult");
            }
        } // ok
    }

    public void test10() {
        int expression = 2;
        switch (expression) { case 1 -> { Runnable result1 = () -> System.out.println("r1"); }
        } // ok
        switch (expression) {
            case 1 -> {
                Runnable result1 = () -> System.out.println("r1");
            }
        } // ok
    }
}
