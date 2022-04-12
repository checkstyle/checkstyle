package org.checkstyle.suppressionxpathfilter.throwscount;

public class SuppressionXpathRegressionThrowsCount1 {
    public void myFunction() throws CloneNotSupportedException,  // warn, max allowed is 4
            ArrayIndexOutOfBoundsException,
            StringIndexOutOfBoundsException,
            IllegalStateException,
            NullPointerException {
        //body
    }
}
