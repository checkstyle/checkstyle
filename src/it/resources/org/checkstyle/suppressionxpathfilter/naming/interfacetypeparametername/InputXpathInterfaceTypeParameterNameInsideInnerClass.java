/*
InterfaceTypeParameterName
format = (default)^[A-Z]$


*/

package org.checkstyle.suppressionxpathfilter.naming.interfacetypeparametername;

public class InputXpathInterfaceTypeParameterNameInsideInnerClass {
  public static class InnerClass {
    public interface InnerInterface<t> { // violation 'Name 't' must match pattern '^[A-Z]$'.'
    }
  }
}
