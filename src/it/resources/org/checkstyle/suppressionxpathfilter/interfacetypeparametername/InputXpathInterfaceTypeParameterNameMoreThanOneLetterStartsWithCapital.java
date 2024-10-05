package org.checkstyle.suppressionxpathfilter.interfacetypeparametername;

public class InputXpathInterfaceTypeParameterNameMoreThanOneLetterStartsWithCapital {
  interface FirstInterface<Type> {} // ok
  interface SecondInterface<type> {} // warn
}
