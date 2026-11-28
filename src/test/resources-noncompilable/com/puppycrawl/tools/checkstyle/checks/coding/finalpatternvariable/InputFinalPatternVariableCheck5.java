/*
FinalPatternVariable


*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

public class InputFinalPatternVariableCheck5 {

    public void assignmentBeforeElseIfDef(Object obj, Object obj2) {
        if (obj instanceof String str) {
            str = "new value";
            System.out.println(str);
        }
        else if (obj2 instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
    }

    public void elseIfDeclaresSameName(Object obj, Object obj2) {
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
        else if (obj2 instanceof String str) {
            str = "new value";
            System.out.println(str);
        }
    }

    public void whileInElseBlock(Object obj, Object obj2) {
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            System.out.println(str);
        }
        else {
            while (obj2 instanceof String str) {
                str = "new value";
                break;
            }
        }
    }

    public void siblingWhilesSameName(Object obj, Object obj2, boolean flag) {
        if (flag) {
            while (obj2 instanceof String str) {
                // violation above "Pattern variable 'str' should be declared final."
                System.out.println(str);
            }
            while (obj instanceof String str) {
                str = "new value";
                break;
            }
        }
    }
}
