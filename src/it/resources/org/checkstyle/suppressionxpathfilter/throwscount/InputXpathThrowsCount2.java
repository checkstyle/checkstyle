package org.checkstyle.suppressionxpathfilter.throwscount;

interface InputXpathThrowsCount2 {
    public void myFunction() throws IllegalStateException, // warn, max allowed is 2
            ArrayIndexOutOfBoundsException,
            NullPointerException;
}
