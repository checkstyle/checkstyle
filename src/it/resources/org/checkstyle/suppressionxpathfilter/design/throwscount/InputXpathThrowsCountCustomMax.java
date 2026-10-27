/*
ThrowsCount
ignorePrivateMethods = (default)true
max = 2


*/

package org.checkstyle.suppressionxpathfilter.design.throwscount;

interface InputXpathThrowsCountCustomMax {
    // violation below 'Throws count is 3 (max allowed is 2).'
    public void myFunction() throws IllegalStateException,
            ArrayIndexOutOfBoundsException,
            NullPointerException;
}
