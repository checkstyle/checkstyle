/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFour {

    class Class3 {
        public void test1() {
            final boolean b = true;
            int shouldBeFinal; // violation, "Variable 'shouldBeFinal' should be declared final"

            if (b) {
                shouldBeFinal = 1;
            }
            else {
                shouldBeFinal = 2;
            }
        }

        public void test2() {
            final int b = 10;
            int shouldBeFinal; // violation, "Variable 'shouldBeFinal' should be declared final"

            switch (b) {
                case 0:
                    shouldBeFinal = 1;
                    break;
                default:
                    shouldBeFinal = 2;
                    break;
            }
        }

        public void test3() {
            int x;        // No Violation
            try {
                x = 0;
            } catch (final Exception e) {
                x = 1;
            }

            int y;        // No Violation
            try {
                y = 0;
            } finally {
                y = 1;
            }
        }

        public void test4() {
            final boolean b = false;
            int x;        // No Violation
            if (b) {
                x = 1;
            } else {
                x = 2;
            }

            if(b) {
                x = 3;
            }
        }

        public void test5() {
            final boolean b = false;
            int shouldBeFinal;    // violation, "Variable 'shouldBeFinal' should be declared final"
            if(b) {
            }
            if (b) {
                shouldBeFinal = 1;
            } else {
                shouldBeFinal = 2;
            }
        }
    }

    class class4 {
        public void foo() {
            int shouldBeFinal;    // violation, "Variable 'shouldBeFinal' should be declared final"
            class Bar {
                void bar () {
                    // violation below "Variable 'shouldBeFinal' should be declared final"
                    int shouldBeFinal;
                    final boolean b = false;
                    if (b) {
                        shouldBeFinal = 1;
                    } else {
                        shouldBeFinal = 2;
                    }
                }
            }
        }
    }

}
