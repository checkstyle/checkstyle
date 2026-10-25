/*
IllegalTokenText
format = 12345
ignoreCase = (default)false
message = (default)
tokens = NUM_INT


*/

package org.checkstyle.suppressionxpathfilter.coding.illegaltokentext;

public class InputXpathIllegalTokenTextField {
    // violation below 'Token text matches the illegal pattern '12345'.'
    private int illegalNumber = 12345;

    public void someMethod() {
        // some code here
    }
}
