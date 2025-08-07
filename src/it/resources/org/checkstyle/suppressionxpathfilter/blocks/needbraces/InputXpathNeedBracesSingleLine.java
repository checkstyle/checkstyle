package org.checkstyle.suppressionxpathfilter.blocks.needbraces;

public class InputXpathNeedBracesSingleLine {
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
