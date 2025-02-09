/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFive {
    class class5 {
        public void test1(){
            final boolean b = false;
            int shouldBeFinal; // violation, "Variable 'shouldBeFinal' should be declared final"
            if(b){
                if(b){
                    shouldBeFinal = 1;
                } else {
                    shouldBeFinal = 2;
                }
            }
        }
        public void test2() {
            final int b = 10;
            int shouldBeFinal; // violation, "Variable 'shouldBeFinal' should be declared final"

            switch (b) {
                case 0:
                    switch (b) {
                        case 0:
                            shouldBeFinal = 1;
                            break;
                        default:
                            shouldBeFinal = 2;
                            break;
                    }
                    break;
                default:
                    shouldBeFinal = 3;
                    break;
            }
        }
        public void test3() {
            int x;    //No Violation
            try {
                x = 0;
                try {
                    x = 0;
                } catch (final Exception e) {
                    x = 1;
                }
            } catch (final Exception e) {
                x = 1;
            }
        }
        public void test4() {
            int shouldBeFinal; // violation, "Variable 'shouldBeFinal' should be declared final"
            class Bar {
                void bar () {
                    // violation below "Variable 'shouldBeFinal' should be declared final"
                    int shouldBeFinal;
                    final boolean b = false;
                    if (b) {
                        if (b) {
                            shouldBeFinal = 1;
                        } else {
                            shouldBeFinal = 2;
                        }
                    } else {
                        shouldBeFinal = 2;
                    }
                }
            }

            abstract class Bar2 {
                abstract void method(String param);
            }
        }

        public void test5() {
            // violation below "Variable 'table' should be declared final"
            InputFinalLocalVariableFive table = new InputFinalLocalVariableFive();
            new Runnable() {
                @Override
                public void run() {
                    InputFinalLocalVariableFive table = null;
                    table = new InputFinalLocalVariableFive();
                }
            };
        }

        public void test6() {
            byte tmpByte[];

            if (true) {
                if (true) {
                    tmpByte = new byte[0];
                }
            } else {
                if (true) {
                    if (true) {
                        tmpByte = new byte[1];
                    }
                }
                if (false) {
                    if (true) {
                        tmpByte = new byte[2];
                    }
                }
            }
        }
    }

}
