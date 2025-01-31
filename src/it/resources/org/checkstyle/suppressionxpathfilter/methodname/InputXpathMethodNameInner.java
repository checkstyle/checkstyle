package org.checkstyle.suppressionxpathfilter.methodname;

public class InputXpathMethodNameInner {
    public void myMethod1() {  
        }
    class Inner {
        public void MyMethod2() { // warn
        }
    }
}
