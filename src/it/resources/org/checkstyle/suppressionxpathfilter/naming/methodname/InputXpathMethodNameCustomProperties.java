/*
MethodName
allowClassName = (default)false
applyToPackage = (default)true
applyToPrivate = (default)true
applyToProtected = false
applyToPublic = false
format = ^[a-z](_?[a-zA-Z0-9]+)*$


*/

package org.checkstyle.suppressionxpathfilter.naming.methodname;

interface Check {
     int i = 10;
     default void FirstMethod() {}
     default void SecondMethod() {}
     // violation below "Name 'ThirdMethod' must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'."
     private void ThirdMethod() {}

}

public class InputXpathMethodNameCustomProperties implements Check {

     @Override
     public void FirstMethod() {

     }

     @Override
     public void SecondMethod() {

     }
}
