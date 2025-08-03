package org.checkstyle.checks.suppressionxpathfilter.interfacetypeparametername;

public class InputXpathInterfaceTypeParameterNameInsideClass {
  interface FirstInterface<T> {}
  interface SecondInterface<t> {} // warn
}
