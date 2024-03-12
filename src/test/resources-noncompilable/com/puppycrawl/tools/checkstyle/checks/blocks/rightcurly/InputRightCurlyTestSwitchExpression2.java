/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_SWITCH, LITERAL_IF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestSwitchExpression2 {

    public void test() {
        int day = 3;
        String check =
            switch (day) {
                case 1 -> "Monday";
                default -> "Invalid day";
            }; }

    public void test1() {
        int day = 3;
        String check =
            switch (day) {
                case 1 -> "Monday";
                default -> "Invalid day";
            };
    }

    public void test2() {
        int day = 3;
        String check =
            switch (day) {
                case 1 -> "Monday";
                default -> "Invalid day";
            }; System.out.println(check);
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
        }
    }

    public void test6() {
        int num = 2;

        switch (num) {
            //no case or default statement
        }
    }

    public void test7() {
        int num = 2;

        switch (num) {
            //no case or default statement
        } int a; // violation ''}' at column 9 should be alone on a line'
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
        }
    }

    public void test10() {
        int expression = 2;
        switch (expression)
        { case 1 -> { Runnable result1 = () -> System.out.println("r1"); } }
        switch (expression)
        { case 1 -> { Runnable result1 = () -> System.out.println("r1"); } }
        switch (expression) {
            case 1 -> {
                Runnable result1 = () -> System.out.println("r1");
            }
        }
    }
}
