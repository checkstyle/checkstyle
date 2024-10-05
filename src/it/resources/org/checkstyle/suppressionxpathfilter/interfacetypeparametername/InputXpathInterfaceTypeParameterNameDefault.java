package org.checkstyle.suppressionxpathfilter.interfacetypeparametername;

public class InputXpathInterfaceTypeParameterNameDefault {
  interface FirstInterface<T> {} // ok
  interface SecondInterface<t> {} // warn
}
