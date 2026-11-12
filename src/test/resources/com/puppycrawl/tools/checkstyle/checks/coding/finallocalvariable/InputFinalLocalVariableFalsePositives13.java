/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives13 {

    // openjdk8/src/share/classes/javax/swing/text/html/parser/Parser.java
    private void foo36() {
        String attname;
        if (true) {
            attname = "";
            if (true) {
            }
            else {
                if (true) {
                    if (true) {
                    }
                    else {
                    }
                }
            }
        }
        else if (true) {
        }
        else if (true) {
            if (true) {
                attname = "";
                if (true) {
                }
                if (true) {
                }
                else {
                    if (true) {
                        if (true) {
                        }
                    }
                }
            }
            else {
            }
        }
        else if (true) {
            attname = "";
        }
        else if (true) {
            return;
        }
        else {
            if (true) {
            }
            else {
                return;
            }
        }
        if (true) {
            attname = "";
        }
        else {
        }
    }

    // openjdk8/src/share/classes/javax/swing/text/JTextComponent.java
    private void foo37() {
        int dot;
        if (true) {
            dot = 213213;
            if (true) {
                if (true) {
                }
            }
            if (true) {
                dot += 1;
                if (true) {
                    try {
                    }
                    catch (Exception ble) {
                    }
                }
            }
        }
        else if (true) {
            dot = 31;
        }
    }

    // openjdk8/src/share/classes/javax/swing/plaf/synth/ParsedSynthStyle.java
    private void foo38() {
        String painter;
        if (true) {
            if ((painter = "").equals("")) {
                return;
            }
        }
        if ((painter = "").equals("")) {
            return;
        }
    }
}
