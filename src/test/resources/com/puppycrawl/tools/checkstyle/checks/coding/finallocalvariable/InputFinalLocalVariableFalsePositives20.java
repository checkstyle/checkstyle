/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives20 {

    // guava-mvnstyle/guava/src/com/google/common/cache/LongAddables.java
    private void foo70() {
        String supplier;
        try {
            supplier = "";
        }
        catch (Throwable t) {
            supplier = "";
        }
    }

    // findbugs/src/java/edu/umd/cs/findbugs/detect/FindNullDeref.java
    private void foo71() {
        int priority;
        if (true) {
            priority = 1;
        }
        else if (true) {
            priority = 2;
        }
        else {
            return;
        }
        if (true) {
            priority++;
        }
        if (true) {
            priority++;
        }
    }

    // findbugs/src/java/edu/umd/cs/findbugs/ba/PruneUnconditionalExceptionThrowerEdges.java
    private void foo72() {
        String p;
        try {
            p = "";
        }
        catch (RuntimeException e) {
            p = "";
        }
    }

    // findbugs/eclipsePlugin/src/de/tobject/findbugs/actions/MarkerRulerAction.java
    private void foo73() {
        String control;
        if (true) {
            if (true) {
                control = "";
                if (control != null) {
                }
            }
        }
        if (true) {
            if (true) {
            }
            if (true) {
                control = "";
                if (control != null) {
                }
            }
        }
    }

    // findbugs/eclipsePlugin/src/de/tobject/findbugs/DetectorsExtensionHelper.java
    private void foo75() {
        String libPathAsString;
        try {
            libPathAsString = "";
            if (libPathAsString == null) {
            }
            libPathAsString = "";
            if (libPathAsString == null) {
            }

        } catch(Exception ex) {

        }
    }

    // findbugs/src/java/edu/umd/cs/findbugs/detect/FindNullDeref.java
    private void foo76() {
        int priority;
        if (true) {
            priority = 2;
        }
        else if (true) {
            priority = 3;
        }
        else {
            return;
        }

        if (true) {
            priority++;
        }
        if (true) {
            priority++;
        }
    }
}
