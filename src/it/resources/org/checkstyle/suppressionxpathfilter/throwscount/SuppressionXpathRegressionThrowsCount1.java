package org.checkstyle.suppressionxpathfilter.throwscount;

public class SuppressionXpathRegressionThrowsCount1 {
    public void myFunction() throws CloneNotSupportedException,
            ArrayIndexOutOfBoundsException,
            StringIndexOutOfBoundsException,
            IllegalStateException,
            NullPointerException { // warn, max allowed is 4
        // body
    }

    public void myFunc() throws ArithmeticException,
            NumberFormatException { // ok
        // body
    }

    private void privateFunc() throws CloneNotSupportedException,
            ClassNotFoundException,
            IllegalAccessException,
            ArithmeticException,
            ClassCastException { // ok, private methods are ignored
        // body
    }
}
