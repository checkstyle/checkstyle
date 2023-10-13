/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = PARAMETER_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariable2Four {
    class Class32 {
        public void test1() {
            final boolean b = true;
            int shouldBeFinal;        //Violation

            if (b) {
                shouldBeFinal = 1;
            }
            else {
                shouldBeFinal = 2;
            }
        }

        public void test2() {
            final int b = 10;
            int shouldBeFinal;        //Violation

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
            int shouldBeFinal;    //Violation
            if(b) {
            }
            if (b) {
                shouldBeFinal = 1;
            } else {
                shouldBeFinal = 2;
            }
        }
    }
}
