package org.checkstyle.suppressionxpathfilter.naming.methodname;

public class InputXpathMethodNameInner {
    public void myMethod1() { // OK
        }
    class Inner {
        public void MyMethod2() { // warn
        }
    }
}
