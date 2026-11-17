/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisPatternVariablesValidateOnlyOverlapping {

    String p;
    String s;

    public void test(Object o) {
        if (o instanceof String p) {
            p.length();
        }
        else {
            p = "outside";
        }
    }
}
