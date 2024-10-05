package org.checkstyle.suppressionxpathfilter.interfacetypeparametername;

public class InputXpathInterfaceTypeParameterNameInsideClass {
  interface FirstInterface<T> {}
  interface SecondInterface<t> {} // warn
}
