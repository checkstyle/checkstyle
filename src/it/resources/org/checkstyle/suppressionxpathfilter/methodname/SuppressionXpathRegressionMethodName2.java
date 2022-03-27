package org.checkstyle.suppressionxpathfilter.methodname;

public class SuppressionXpathRegressionMethodName2 {
    public void myMethod1() { // OK
        }
    class Inner {
        public void MyMethod2() { // warn
        }
    }
}
