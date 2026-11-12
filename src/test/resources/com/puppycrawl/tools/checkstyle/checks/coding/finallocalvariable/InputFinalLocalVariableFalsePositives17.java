/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives17 {

    // openjdk8/src/share/classes/java/awt/geom/CubicCurve2D.java
    private void foo56() {
        int num;
        if (true) {
            num = 3;
        }
        else {
            num = 1;
            if (true) {
                num = 2;
            }
        }
        if (num > 1) {
            num = 2;
        }
    }

    // openjdk8/src/share/classes/java/awt/geom/AffineTransform.java
    private void foo57() {
        final int a = 1;
        double T01, T10;
        switch (a) {
            case 1:
                break;
            case 9:
            case 11:
            case 12:
                break;
            case 123:
            case 14:
                break;
            case 1234:
            case 12345:
                break;
            case 566:
            case 8678:
                break;
            case 534:
            case 7968:
            case 86785678:
            case 86759789:
                break;
            case 346547:
                T01 =1;
                T10 = 2;
                break;
            case 432534523:
            case 5345235:
                break;
            case 235345:
            case 523452345:
                break;
            case 75675467:
                break;
        }
        T01 = 21;
        T10 = 53;
    }

    // openjdk8/src/share/classes/java/awt/event/WindowEvent.java
    private void foo58() {
        final int a = 54;
        String typeStr;
        switch (a) {
            case 1:
                typeStr = "WINDOW_OPENED";
                break;
            case 2:
                typeStr = "WINDOW_CLOSING";
                break;
            case 3:
                typeStr = "WINDOW_CLOSED";
                break;
            case 4:
                typeStr = "WINDOW_ICONIFIED";
                break;
            case 5:
                typeStr = "WINDOW_DEICONIFIED";
                break;
            case 6:
                typeStr = "WINDOW_ACTIVATED";
                break;
            case 7:
                typeStr = "WINDOW_DEACTIVATED";
                break;
            case 8:
                typeStr = "WINDOW_GAINED_FOCUS";
                break;
            case 9:
                typeStr = "WINDOW_LOST_FOCUS";
                break;
            case 10:
                typeStr = "WINDOW_STATE_CHANGED";
                break;
            default:
                typeStr = "unknown type";
        }
        typeStr += ",";
    }
}
