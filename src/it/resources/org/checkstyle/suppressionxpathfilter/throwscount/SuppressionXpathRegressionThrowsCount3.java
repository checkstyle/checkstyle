package org.checkstyle.suppressionxpathfilter.throwscount;

public class SuppressionXpathRegressionThrowsCount3 {
    interface myClass{
        //body
    }
    public void myFunc() {
        myClass foo = new myClass() {
            private void privateFunc() throws CloneNotSupportedException, // warn, max allowed is 4
                    ClassNotFoundException,
                    IllegalAccessException,
                    ArithmeticException,
                    ClassCastException {
                // body
            }
        };
    }
}
