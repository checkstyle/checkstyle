/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives12 {

    // openjdk8/src/share/classes/javax/swing/text/html/parser/Parser.java
    private void foo35() {
        final int a = 8;
        String elem;
        switch (a) {
            case 1:
                switch (a) {
                    case '-':
                        while (true) {
                            if (true) {
                            }
                        }
                    default:
                }
            case '/':
                switch (a) {
                    case '>':
                    case '<':
                        if (true) {

                        }
                        elem = "";
                        break;
                    default:
                        if (true) {
                            return;
                        }
                        switch (a) {
                            case '>':
                            case '<':
                                break;
                            default:
                        }
                        if (true) {
                            elem = "";
                        }
                        else {
                            elem = "";
                        }
                        break;
                }
                if (true) {
                    elem = "";
                    if (true) {
                        return;
                    }
                }
                else {
                    if (true) {
                        elem = "";
                    }
                    else {
                        elem = "";
                    }
                }
                elem = "";
        }
    }
}
