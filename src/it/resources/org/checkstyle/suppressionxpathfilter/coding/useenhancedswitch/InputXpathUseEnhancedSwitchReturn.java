package org.checkstyle.suppressionxpathfilter.coding.useenhancedswitch;

public class InputXpathUseEnhancedSwitchReturn {
     void test(int x) {
        for (int i = 0; i < 1; i++) {
            switch (x) { // warn
                case 1:
                    System.out.println("one");
                    break;
                case 2:
                    System.out.println("two");
                    break;
                default:
                    System.out.println("other");
            }
        }
    }
}
