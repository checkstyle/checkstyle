/*
MethodTypeParameterName
format = (default)^[A-Z]$


*/

package org.checkstyle.suppressionxpathfilter.naming.methodtypeparametername;

public class InputXpathMethodTypeParameterNameDefault {
  public <TT> void foo() { } // violation 'Name 'TT' must match pattern '^[A-Z]$'.'
}
