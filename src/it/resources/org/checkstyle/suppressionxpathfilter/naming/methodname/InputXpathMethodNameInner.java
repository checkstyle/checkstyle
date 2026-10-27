/*
MethodName
allowClassName = (default)false
applyToPackage = (default)true
applyToPrivate = (default)true
applyToProtected = (default)true
applyToPublic = (default)true
format = ^[a-z](_?[a-zA-Z0-9]+)*$


*/

package org.checkstyle.suppressionxpathfilter.naming.methodname;

public class InputXpathMethodNameInner {
    public void myMethod1() {
        }
    class Inner {
        // violation below "Name 'MyMethod2' must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'."
        public void MyMethod2() {
        }
    }
}
