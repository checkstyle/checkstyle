package org.checkstyle.suppressionxpathfilter.methodname;

public class InputXpathMethodName2 {
    public void myMethod1() { // OK
        }
    class Inner {
        public void MyMethod2() { // warn
        }
    }
}
