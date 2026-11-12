/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives14 {

    // openjdk8/src/share/classes/javax/swing/plaf/metal/MetalInternalFrameTitlePane.java
    private void foo39() {
        int spacing;
        if (true) {
            if (true) {
                if (true);
            }
            else {
                spacing = 4;
                if (true) return;
            }
        }
        if (true) {
            spacing = 123;
            if (true) return;
        }
        if (true) {
            spacing = 12;
        }
    }

    // openjdk8/src/share/classes/javax/swing/plaf/basic/BasicBorders.java
    private void foo40() {
        String cBounds;
        if (true) {
            if (true) {
                cBounds = "";
            }
            if (true) {
                cBounds = "";
            }
        }
        else {
            if (true) {
                cBounds = "";
            }
            if (true) {
                cBounds = "";
            }
        }
    }

    // openjdk8/src/share/classes/javax/swing/border/CompoundBorder.java
    private void foo41() {
        String nextInsets;
        if (true) {
            nextInsets = "";
        }
        if (true) {
            nextInsets = "";
        }
    }

    // openjdk8/src/share/classes/javax/swing/JTree.java
    private String foo42() {
        if (true) {
            String rowBounds;
            if (true) {
                rowBounds = "";
                if (true) {
                    if (true) {
                        return "";
                    }
                    return "";
                }
                if (true) {
                    if (true) {
                        rowBounds = "";
                        return rowBounds;
                    }
                }
                else {
                    return rowBounds;
                }
            }
            return "";
        }
        return "";
    }

    // openjdk8/src/share/classes/javax/sql/rowset/spi/SyncFactory.java
    private void foo43() {
        String providerImpls;
        try {
            providerImpls = "";
        }
        catch (Exception ex) {
            providerImpls = null;
        }
        String strRowsetProperties;
        try {
            strRowsetProperties = "";
        }
        catch (Exception ex) {
            strRowsetProperties = "";
        }
    }
}
