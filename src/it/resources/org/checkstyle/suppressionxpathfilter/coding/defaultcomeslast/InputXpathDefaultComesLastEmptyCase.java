/*
DefaultComesLast
skipIfLastAndSharedWithCase = true


*/

package org.checkstyle.suppressionxpathfilter.coding.defaultcomeslast;

public class InputXpathDefaultComesLastEmptyCase {
    public void test(int i) {
        switch (i) {
            case 1:
            default:
                break;
            case 2:
                break;
        }

        switch (i) {
            case 1:
            default: // violation 'Default should be last label in the case group.'
            case 2:
                break;
            case 3:
                break;
        }
    }
}
