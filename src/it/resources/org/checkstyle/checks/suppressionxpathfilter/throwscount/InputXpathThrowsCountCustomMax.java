package org.checkstyle.checks.suppressionxpathfilter.throwscount;

interface InputXpathThrowsCountCustomMax {
    public void myFunction() throws IllegalStateException, // warn, max allowed is 2
            ArrayIndexOutOfBoundsException,
            NullPointerException;
}
