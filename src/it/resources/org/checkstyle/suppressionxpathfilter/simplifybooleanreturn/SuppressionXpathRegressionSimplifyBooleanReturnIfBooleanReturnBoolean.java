package org.checkstyle.suppressionxpathfilter.simplifybooleanreturn;

public class SuppressionXpathRegressionSimplifyBooleanReturnIfBooleanReturnBoolean {
    public static boolean toTest() {
        boolean even = false;
        if (!even) { // warn
            return true;
        }
        else {
            return false;
        }
    }
}
