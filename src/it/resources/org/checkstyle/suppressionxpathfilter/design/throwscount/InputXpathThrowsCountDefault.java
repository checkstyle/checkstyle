/*
ThrowsCount
ignorePrivateMethods = (default)true
max = (default)4


*/

package org.checkstyle.suppressionxpathfilter.design.throwscount;

public class InputXpathThrowsCountDefault {
    // violation below 'Throws count is 5 (max allowed is 4).'
    public void myFunction() throws CloneNotSupportedException,
            ArrayIndexOutOfBoundsException,
            StringIndexOutOfBoundsException,
            IllegalStateException,
            NullPointerException {
        //body
    }
}
