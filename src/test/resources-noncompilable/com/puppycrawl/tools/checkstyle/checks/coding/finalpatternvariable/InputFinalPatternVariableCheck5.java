/*
FinalPatternVariable


*/

// non-compiled with javac: Compilable with Java25
// pattern variable is already defined in the enclosing scope
package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

public class InputFinalPatternVariableCheck5 {

    public void assignmentBeforeNestedIf(Object obj, Object obj2, boolean flag) {
        if (obj instanceof String str) {
            if (flag) {
                str = "new value";
                if (obj2 instanceof String str) {
                    // violation above "Pattern variable 'str' should be declared final."
                    System.out.println(str);
                }
            }
            System.out.println(str);
        }
    }

    public void nestedIfDeclaresSameName(Object obj, Object obj2) {
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            if (obj2 instanceof String str) {
                str = "new value";
                System.out.println(str);
            }
            System.out.println(str);
        }
    }

    public void whileNestedInIfBody(Object obj, Object obj2) {
        if (obj instanceof String str) {
            // violation above "Pattern variable 'str' should be declared final."
            while (obj2 instanceof String str) {
                str = "new value";
                break;
            }
            System.out.println(str);
        }
    }

    public void whileCoveringWithInnerDef(Object obj, Object obj2, boolean flag) {
        if (flag) {
            while (obj instanceof String str) {
                if (obj2 instanceof String str) {
                    // violation above "Pattern variable 'str' should be declared final."
                    System.out.println(str);
                }
                str = "new value";
                break;
            }
        }
    }
}
