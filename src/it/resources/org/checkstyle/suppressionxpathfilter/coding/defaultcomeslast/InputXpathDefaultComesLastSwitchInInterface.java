/*
DefaultComesLast
skipIfLastAndSharedWithCase = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.defaultcomeslast;

public interface InputXpathDefaultComesLastSwitchInInterface {
    default void test(int x) {
        switch (x) {
            default: // violation 'Default should be last label in the switch.'
                break;
            case 1:
                break;
        }
    }
}
