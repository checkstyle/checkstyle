package org.checkstyle.suppressionxpathfilter.coding.useenhancedswitch;

public class InputXpathUseEnhancedSwitchSimple {
    public void test() {
        int id = 0;
        switch (id) { //warn
            case 0:
                System.err.println("zero");
                break;
            case 1:
                System.err.println("one");
                break;
            default:
        }
    }
}
