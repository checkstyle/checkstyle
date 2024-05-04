package org.checkstyle.suppressionxpathfilter.throwscount;

public class InputXpathThrowsCountPrivateMethods {
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
