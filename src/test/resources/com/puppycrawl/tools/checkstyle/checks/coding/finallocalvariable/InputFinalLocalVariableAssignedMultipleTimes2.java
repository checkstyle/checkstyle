/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableAssignedMultipleTimes2 {

    void foo8() {
        final boolean some_condition = true;
        final int i;
        if (some_condition) {
            i = 1;
        }
        else {

        }
    }

    // Taken from findbugs
    public static String foo9(String filePath, String project) {
        String path = new String(filePath); // violation "Variable 'path' should be declared final"
        String commonPath;
        if (project != null) {
            commonPath = "";
            // violation below "Variable 'relativePath' should be declared final"
            String relativePath = "";
            if (!relativePath.equals(path)) {
                return relativePath;
            }
        }
        commonPath = "";
        return commonPath;
    }

    // Taken from findbugs
    public int foo10(String factory1, String factory2) {
        int result = 0;
        String s1, s2;
        switch (result) {
            case 1:
                s1 = "Java";
                s2 = "C#";
                break;
            case 2:
                s1 = "C++";
                s2 = "Pascal";
                ;
                break;
            case 3:
                s1 = "Basic";
                s2 = "Angol";
                break;
            case 4:
                s1 = "JavaScript";
                s2 = "Go";
                break;
            case 5:
            default:
                s1 = "F#";
                s2 = "Objective C";
                break;
        }
        if (s1 == null) {
            s1 = "incorrect language";
        }
        if (s2 == null) {
            s2 = "incorrect language";
        }
        result = s1.compareTo(s2);

        if (result == 0) {
            switch (result) {
                case 1:
                    s1 = "Java";
                    s2 = "C#";
                    break;
                case 2:
                default:
                    s1 = "C++";
                    s2 = "C";
                    break;
            }
            result = s1.compareTo(s2);
        }
        else if (result == 1) {
            result = -8;
        }
        return result;
    }
}
