/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableSwitchStatement {
    public void testParameters0() {

        int i = 0;
        switch (i++) {
            default:
            case 0:
                System.out.println("one: ");
                break;
            case 1:
                System.out.println("two: " );
                break;
        }

    }

    public void testParameters01() {
        int i = 0;
        switch (++i) {
            default:
            case 0:
                System.out.println("one: ");
                break;
            case 1:
                System.out.println("two: " );
                break;
        }

    }

    public void testParameters() {
        String[] tests = { "one", "two" };
        int i = 0;
        for (String test : tests) {
            switch (i++) {
                default:
                case 0:
                    System.out.println("one: " + test);
                    break;
                case 1:
                    System.out.println("two: " + test);
                    break;
            }
        }
    }

    public void testParameters2() {
        String[] tests = { "one", "two" };
        int i = 0;
        for (String test : tests) {
            switch (++i) {
                default:
                case 0:
                    System.out.println("one: " + test);
                    break;
                case 1:
                    System.out.println("two: " + test);
                    break;
            }
        }
    }

    public void testParameters3() {
        String[] tests = { "one", "two" };
        int i = 0;
        for (String test : tests) {
            switch (--i) {
                default:
                case 0:
                    System.out.println("one: " + test);
                    break;
                case 1:
                    System.out.println("two: " + test);
                    break;
            }
        }
    }

    public void testParameters4() {
        String[] tests = { "one", "two" };
        int i = 0;
        for (String test : tests) {
            switch (i--) {
                default:
                case 0:
                    System.out.println("one: " + test);
                    break;
                case 1:
                    System.out.println("two: " + test);
                    break;
            }
        }
    }
}
