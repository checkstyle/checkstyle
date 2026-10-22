/*
DefaultComesLast
skipIfLastAndSharedWithCase = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.defaultcomeslast;

public class InputXpathDefaultComesLastNonEmptyCase {
    public void test() {
        String lang = "Java";
        int id = 0;
        switch (lang) {
            default : id = -1; // violation 'Default should be last label in the switch.'
            case "C++" : id = 1; break;
            case "Python" : id = 2; break;
        }
    }
}
