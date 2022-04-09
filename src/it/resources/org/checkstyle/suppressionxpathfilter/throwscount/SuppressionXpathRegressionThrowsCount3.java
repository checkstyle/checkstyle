package org.checkstyle.suppressionxpathfilter.throwscount;

interface SuppressionXpathRegressionThrowsCount3 {
    private void privateFunc() throws CloneNotSupportedException, // warn, max allowed is 4
            ClassNotFoundException,
            IllegalAccessException,
            ArithmeticException,
            ClassCastException {
        // body
    }
}
