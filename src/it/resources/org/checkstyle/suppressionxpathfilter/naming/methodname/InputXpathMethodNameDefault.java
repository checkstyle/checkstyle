/*
MethodName
allowClassName = (default)false
applyToPackage = (default)true
applyToPrivate = (default)true
applyToProtected = (default)true
applyToPublic = (default)true
format = (default)^[a-z][a-zA-Z0-9]*$


*/

package org.checkstyle.suppressionxpathfilter.naming.methodname;

public class InputXpathMethodNameDefault {

  protected void firstMethod() {}
  // violation below "Name 'SecondMethod' must match pattern '^[a-z][a-zA-Z0-9]*$'."
  private void SecondMethod() {}

}
