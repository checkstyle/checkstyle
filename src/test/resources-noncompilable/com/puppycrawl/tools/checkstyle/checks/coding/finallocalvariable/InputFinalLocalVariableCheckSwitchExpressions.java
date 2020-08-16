//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

/* Config:
 *
 * validateEnhancedForLoopVariable = false
 * tokens = {VARIABLE_DEF}
 */
public class InputFinalLocalVariableCheckSwitchExpressions {
    void foo1() throws Exception {

        Exception e; // violation

        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
            case 0:
                e = new Exception();
                break;
            case 1:
                if (b == 0) {
                    e = new Exception();
                    break;
                }

                if (b == 1) {
                    e = new Exception();
                } else {
                    e = new Exception();
                }
                break;
            case 2:
                if (b == 0) {
                    return;
                }

                e = new Exception();
                break;
            default:
                e = new Exception();
                break;
        }

        throw e;
    }

    void foo2() throws Exception {

        Exception e; // violation

        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
            case 0 -> {
                e = new Exception();
            }
            case 1 -> {
                if (b == 0) {
                    e = new Exception();
                    break;
                }

                if (b == 1) {
                    e = new Exception();
                } else {
                    e = new Exception();
                }
            }
            case 2 -> {
                if (b == 0) {
                    return;
                }

                e = new Exception();
            }
            default -> {
                e = new Exception();
            }
        }

        throw e;
    }

    void foo3() throws Exception {

        Exception e; // violation

        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
            case 0 -> e = new Exception();
            case 1 -> {
                if (b == 0) {
                    e = new Exception();
                    break;
                }

                if (b == 1) {
                    e = new Exception();
                } else {
                    e = new Exception();
                }
            }
            case 2 -> {
                if (b == 0) {
                    return;
                }

                e = new Exception();
            }
            default -> e = new Exception();
        }

        throw e;
    }

    void foo4() throws Exception {

        Exception e; // violation

        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
            case 0 -> e = new Exception();
            case 1 -> System.out.println("test!");
            default -> System.out.println("Exception!");

        }

    }
}

