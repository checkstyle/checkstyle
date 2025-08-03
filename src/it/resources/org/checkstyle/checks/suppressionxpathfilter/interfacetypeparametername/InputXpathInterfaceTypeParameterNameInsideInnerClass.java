package org.checkstyle.checks.suppressionxpathfilter.interfacetypeparametername;

public class InputXpathInterfaceTypeParameterNameInsideInnerClass {
  public static class InnerClass {
    public interface InnerInterface<t> { // warn
    }
  }
}
