package org.checkstyle.suppressionxpathfilter.interfacetypeparametername;

public class InputXpathInterfaceTypeParameterNameOnlySingleLetter {
  interface FirstInterface<T> {} // ok
  interface SecondInterface<t> {} // ok
  interface ThirdInterface<Type> {} // warn
}
