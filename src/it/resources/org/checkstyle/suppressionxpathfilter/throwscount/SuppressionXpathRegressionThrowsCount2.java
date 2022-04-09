package org.checkstyle.suppressionxpathfilter.throwscount;

public class SuppressionXpathRegressionThrowsCount2 {
    interface myInterface {
        public void myFunction() throws IllegalStateException, // warn, max allowed is 2
                ArrayIndexOutOfBoundsException,
                NullPointerException;
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
