package org.checkstyle.suppressionxpathfilter.throwscount;

public class SuppressionXpathRegressionThrowsCount3 {

    public void myFunc() throws ArithmeticException,
            NumberFormatException { // ok
        // body
    }

    private void privateFunc() throws CloneNotSupportedException, // warn, max allowed is 4
            ClassNotFoundException,
            IllegalAccessException,
            ArithmeticException,
            ClassCastException {
        // body
    }

    private void func() throws IllegalStateException,
            NullPointerException { // ok
        // body
    }
}
