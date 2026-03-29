package org.checkstyle.suppressionxpathfilter.coding.defaultcomeslast;

public interface InputXpathDefaultComesLastSwitchInInterface {
    default void test(int x) {
        switch (x) {
            default: // warn
                break;
            case 1:
                break;
        }
    }
}
