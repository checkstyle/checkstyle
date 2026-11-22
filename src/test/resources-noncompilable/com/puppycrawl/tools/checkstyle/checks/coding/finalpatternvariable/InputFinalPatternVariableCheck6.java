/*
FinalPatternVariable


*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.coding.finalpatternvariable;

public class InputFinalPatternVariableCheck6 {

    public void unnamedPatternNested(Object obj, Object obj2) {
        if (obj instanceof String str) {
            if (obj2 instanceof String _) {
                // violation above "Pattern variable '_' should be declared final."
                str = "new value";
            }
            System.out.println(str);
        }
    }
}
