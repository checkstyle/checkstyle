/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives4 {

    // openjdk8/src/share/classes/sun/tools/jar/Main.java
    private void foo13() {
        String out;
        if (true) {
            out = "";
        }
        else {
            out = "";
            if (true) {
            }
        }
        if (true) {
            out = "";
        }
        if (true) {
        }
        out.toString();
        if (true) {
            try {
                out = "";
            }
            catch (Exception ioe) {
            }
            finally {
                if (true) {
                }
                if (true) {
                    out.toString();
                }
                if (true) {
                }
                if (true) {
                }
                if (true) {
                }
            }
        }
    }

    // openjdk8/src/share/classes/sun/text/normalizer/NormalizerBase.java
    private void foo14() {
        int c, c2; // violation "Variable 'c2' should be declared final"
        if (true) {
        }
        if (true) {
            if ((c = 'd') >= 0) {
                if (true) {
                    c2 = 'a';
                    if (true) {
                        if (true) {
                            if (true) {
                            }
                            c = c2;
                        }
                        else {
                        }
                    }
                }

                if (true) {
                }
            }
            return;
        }

    }

    // openjdk8/src/share/classes/sun/text/normalizer/CharTrie.java
    private void foo15() {
        int limit;
        if (true) {
            limit = 0xdc00 >> 1;
            limit = 2 + 1;

        }
    }
}
