package org.checkstyle.suppressionxpathfilter.interfacetypeparametername;

public class InputXpathInterfaceTypeParameterNameOnlySingleLetterLowercase {
  interface FirstInterface<T> {} // warn
  interface SecondInterface<t> {} // ok
}
