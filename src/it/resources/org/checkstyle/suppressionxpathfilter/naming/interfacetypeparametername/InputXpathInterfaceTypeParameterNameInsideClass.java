package org.checkstyle.suppressionxpathfilter.naming.interfacetypeparametername;

public class InputXpathInterfaceTypeParameterNameInsideClass {
  interface FirstInterface<T> {}
  interface SecondInterface<t> {} // warn
}
