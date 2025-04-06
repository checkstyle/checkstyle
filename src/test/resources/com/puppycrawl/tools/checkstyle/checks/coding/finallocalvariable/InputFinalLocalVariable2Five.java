/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = PARAMETER_DEF
validateUnnamedVariables = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariable2Five {
    class class42 {
        public void foo() {
            int shouldBeFinal;
            class Bar {
                void bar () {
                    int shouldBeFinal;    //Violation
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

    class class52 {
        public void test1(){
            final boolean b = false;
            int shouldBeFinal;    //Violation
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
            int shouldBeFinal;        //Violation

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
            int shouldBeFinal;
            class Bar {
                void bar () {
                    int shouldBeFinal;    //Violation
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
            InputFinalLocalVariableFive table
                    = new InputFinalLocalVariableFive();
            new Runnable() {
                @Override
                public void run() {
                    InputFinalLocalVariableFive table = null;
                    table = new InputFinalLocalVariableFive();
                }
            };
        }
    }

}
