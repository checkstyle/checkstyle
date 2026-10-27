/*
ThrowsCount
ignorePrivateMethods = false
max = (default)4


*/

package org.checkstyle.suppressionxpathfilter.design.throwscount;

public class InputXpathThrowsCountPrivateMethods {
    interface myClass{
        //body
    }
    public void myFunc() {
        myClass foo = new myClass() {
            // violation below 'Throws count is 5 (max allowed is 4).'
            private void privateFunc() throws CloneNotSupportedException,
                    ClassNotFoundException,
                    IllegalAccessException,
                    ArithmeticException,
                    ClassCastException {
                // body
            }
        };
    }
}
