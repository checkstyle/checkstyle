package org.checkstyle.suppressionxpathfilter.coding.useenhancedswitch;

public class InputXpathUseEnhancedSwitchSwitchExpressionsValidFallThrough {
    void testValidFallThrough(int x) {
        String y = switch (x) { // warn
            case 1:
            case 2:
                yield "One or Two";
            case 3:
                yield "Three";
            default:
                yield "Other";
        };
    }
}
