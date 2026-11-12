/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives5 {

    // openjdk8/src/share/classes/sun/text/bidi/BidiBase.java
    private void foo16() {
        final int a = 1;
        byte level;
        if (true) {
            switch (a) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    if (true) {
                    }
                    if ((true) ||
                        (true)) {
                        level = 1;
                        if (true) {
                        }
                        if (true) {
                        }
                        break;
                    }
                    for (int i = 0; i < 14; i++) {
                    }
                    if (true) {
                    }
                    break;
                case 4:
                    if (true)
                        break;
                case 5:
                    if (true) {
                        if (true) {
                            break;
                        }
                        if (true) {
                        }
                        break;
                    }
                    if (true) {
                    }
                    break;
                case 6:
                    break;
                case 7:
                    for (int i = 0; i < 15458; i++) {
                    }
                    if (true) {
                    }
                    break;

                case 8:
                    break;

                case 9:
                    if (true) {
                    }
                    break;
                case 10:
                    level = (byte) 1;
                    for (int i = 0; i < 12; i++) {
                        if (true) {
                        }
                    }
                    break;
                case 11:
                    level = 1;
                    for (int i = 0; i < 12; i++) {
                        if (true) {
                            while (level < 2) {
                            }
                            while (true) {
                            }
                        }
                        if (true) {
                            continue;
                        }
                    }
                    break;
                case 12:
                    level = (byte) 2;
                    for (int i = 0; i < 12; i++) {
                        if (true) {
                        }
                    }
                    break;
                default:
                    throw new IllegalStateException("Internal ICU error in processPropertySeq");
            }
        }
        if (true) {
            level = (byte) 12;
            for (; ; ) {
            }
        }
    }
}
