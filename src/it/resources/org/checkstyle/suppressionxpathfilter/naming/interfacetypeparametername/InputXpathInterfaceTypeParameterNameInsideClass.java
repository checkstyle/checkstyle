/*
InterfaceTypeParameterName
format = (default)^[A-Z]$


*/

package org.checkstyle.suppressionxpathfilter.naming.interfacetypeparametername;

public class InputXpathInterfaceTypeParameterNameInsideClass {
  interface FirstInterface<T> {}
  interface SecondInterface<t> {} // violation 'Name 't' must match pattern '^[A-Z]$'.'
}
