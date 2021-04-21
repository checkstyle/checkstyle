package org.checkstyle.suppressionxpathfilter.needbraces;

public class SuppressionXpathRegressionNeedBracesSingleLine {
    private static class SomeClass {
        boolean flag = true;
        private static boolean test(boolean k) {
            return k;
        }
    }

    /** Test do/while loops **/
    public int test() {
        // Invalid
        if (SomeClass.test(true) == true) // warn
            return 4;
        return 0;
    }
}
