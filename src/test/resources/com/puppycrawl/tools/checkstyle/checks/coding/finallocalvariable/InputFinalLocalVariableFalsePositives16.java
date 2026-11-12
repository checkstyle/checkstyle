/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositives16 {

    // openjdk8/src/share/classes/java/text/CollationElementIterator.java
    private void foo51() {
        if (true) {
            int vowel;
            if (true) {
                vowel = 1;
            }
            if (true) {
                vowel = 2;
            }
        }
        if (true) {
            int consonant;
            if (true) {
                consonant = 23;
            }
            if (true) {
                consonant =2;
            }
        }
    }

    // openjdk8/src/share/classes/java/sql/DriverManager.java
    private void foo52() {
        String drivers;
        try {
            drivers = "";
        }
        catch (Exception ex) {
            drivers = null;
        }
    }

    // openjdk8/src/share/classes/java/security/Policy.java
    private void foo53() {
        String pc;
        synchronized (this) {
            pc = "Hello";
        }
        pc = "";
    }

    // openjdk8/src/share/classes/java/lang/invoke/DirectMethodHandle.java
    private void foo54() {
        final int a = 1;
        final String linkerName;
        String lambdaName;
        switch (a) {
            case 1:
                linkerName = "linkToVirtual";
                lambdaName = "DMH"
                    + ".invokeVirtual";
                break;
            case 2:
                linkerName = "linkToStatic";
                lambdaName = "DMH"
                    + ".invokeStatic";
                break;
            case 3:
                linkerName = "linkToStatic";
                lambdaName = "DMH.invokeStaticInit";
                break;
            case 4:
                linkerName = "linkToSpecial";
                lambdaName = "DMH"
                    + ".invokeSpecial";
                break;
            case 5:
                linkerName = "linkToInterface";
                lambdaName = "DMH"
                    + ".invokeInterface";
                break;
            case 6:
                linkerName = "linkToSpecial";
                lambdaName = "DMH"
                    + ".newInvokeSpecial";
                break;
            default:
                throw new InternalError("which=");
        }
        lambdaName += "_";
    }

    // openjdk8/src/share/classes/java/lang/Class.java
    private String foo55() {
        String res;
        if (true) {
            res = "";
            if (res != null) return res;
        }
        if (true) {
            res = "temporaryRes";
        }
        else {
            res = "";
        }
        if (true) {
            if (true) {
            }
            else {
            }
        }
        return "";
    }
}
