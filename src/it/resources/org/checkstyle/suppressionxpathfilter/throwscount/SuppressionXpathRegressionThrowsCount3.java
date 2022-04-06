package org.checkstyle.suppressionxpathfilter.throwscount;

public class SuppressionXpathRegressionThrowsCount3 {
    public void myFunction() throws CloneNotSupportedException,
            ArrayIndexOutOfBoundsException,
            StringIndexOutOfBoundsException,
            IllegalStateException,
            NullPointerException {
        // body
    }

    public void myFunc() throws ArithmeticException,
            NumberFormatException { // ok
        // body
    } // warn

    private void privateFunc() throws CloneNotSupportedException,
            ClassNotFoundException,
            IllegalAccessException,
            ArithmeticException,
            ClassCastException { // warn, max allowed is 4
        // body
    }

    private void func() throws IllegalStateException,
            NullPointerException { // ok
        // body
    }
}
