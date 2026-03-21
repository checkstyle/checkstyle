/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableSwitchStatement2 {
    public void testParameters0() {

        int i = 0;
        switch (i+1) {
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
        switch (1+i) {
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
            switch (i+1) {
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
        i++;
        int j = 0; // violation, 'Unused local variable 'j''
        for (String test : tests) {
            switch (i) {
                default:
                case 0:
                    j++;
                    System.out.println("one: " + test);
                    break;
                case 1:
                    System.out.println("two: " + test);
                    break;
            }
        }
    }

    public void testParameters4() {
        int i = 2;
        int j = 1;
        switch (j) {
            case 1:
                System.out.println("j is 1");
                break;
            case 2:
                System.out.println("j is 2");
                System.out.println("i is: " + i);
                break;
            default:
                System.out.println("j is something else");
        }
    }

    public void testParameters5() {
        int i = 2;
        int j = 1;
        switch (++j) {
            case 1:
                System.out.println("j is 1");
                break;
            case 2:
                System.out.println("j is 2");
                System.out.println("i is: " + ++i);
                break;
            default:
                System.out.println("j is something else");
        }
    }
}
