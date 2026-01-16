package org.checkstyle.suppressionxpathfilter.coding.useenhancedswitch;

public class InputXpathUseEnhancedSwitchExpressions {
    public void test(int input) {
        int id = switch (input) { // warn
            case 0:
                System.err.println("zero");
                yield 0;
            case 1:
                System.err.println("one");
                yield 1;
            default:
                yield -1;
        };
    }
}
