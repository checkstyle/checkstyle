/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFallThroughCaseGroup {
    void foo() {
        int a; // ok
        switch ((int) Math.random()) {
            case 0:
                a = 0;
            default:
                a = 2;
                break;
        }
    }

    void foo2() throws Exception {
        int a; // ok
        int b; // violation 'Variable .* should be declared final.'
        switch ((int) Math.random()) {
            case 0:
                a = 0;
                b = 0;
                throw new Exception();
            case 1:
            case 2:
                a = 1;
            default:
                a = 2;
                b = 0;
                throw new Exception();
        }
    }

        void foo3() {
            int a; // ok
            switch ((int) Math.random()) {
                case 0:
                    a = 0;
                    break;
                case 1:
                case 2:
                    a = 1;
                default:
                    a = 2;
                    break;
            }
        }

    int foo4() {
        int a; // ok
        switch ((int) Math.random()) {
            case 0:
                a = 0;
            default:
                a = 2;
                return 1;
        }
    }

    int foo5() {
        int a; // ok
        switch ((int) Math.random()) {
            case 0:
            case 1:
            case 2:
                a = 1;
            default:
                a = 2;
                return 1;
        }
    }

    int foo6() {
        int a; // ok
        int b; // violation 'Variable .* should be declared final.'
        switch ((int) Math.random()) {
            case 0:
                a = 0;
                b = 0;
                return 1;
            case 1:
            case 2:
                a = 1;
            default:
                a = 2;
                b = 0;
                return 1;
        }
    }

    int foo7() throws Exception {
        int a; // ok
        switch ((int) Math.random()) {
            case 0:
                a = 0;
                return 1;
            case 1:
                a = 10;
                throw new Exception();
            case 2:
                a = 1;
            case 3:
                a = 2;
                break;
            default:
                a = 2;
                return 0;
        }
        return 0;
    }
}
