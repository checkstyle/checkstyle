/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives11 {

    // openjdk8/src/share/classes/sun/font/FileFontStrike.java
    private void foo33() {
        float advance;
        if (true) {
            advance = 1234;
            if (advance != Float.MAX_VALUE) {
                if (true) {
                    return;
                }
                else {
                    return;
                }
            }
        }
        else if (true) {
            if (true) {
                advance = 123;
                if (advance != Float.MAX_VALUE) {
                    if (true) {
                        return;
                    }
                    else {
                        return;
                    }
                }
            }
        }
        if (true) {
            return;
        }
        if (true) {
            advance = 12435;
        }
        else {
            if (true) {
            }
            else {
            }
            if (true) {
                advance = 123;
            }
            else {
                advance = 123414;
            }
        }
        if (true) {
        }
        else if (true) {
            if (true) {
                for (int i = 0; i < 214; i++) {
                }
            }
        }
        return;
    }

    // openjdk8/src/share/classes/sun/awt/geom/AreaOp.java
    private void foo34() {
        final String s = "";
        int etag;
        if (true) {
            etag = 1;
            do {
                if (true) {
                }
                if (true) {
                }
            } while ("".equals(s));
            if (true) {
                etag = 13213;
            }
            else {
            }
        }
        else {
            etag = 12312;
        }
    }
}
