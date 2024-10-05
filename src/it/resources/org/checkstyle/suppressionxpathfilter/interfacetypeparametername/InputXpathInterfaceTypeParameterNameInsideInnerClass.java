package org.checkstyle.suppressionxpathfilter.interfacetypeparametername;

public class InputXpathInterfaceTypeParameterNameInsideInnerClass {
  public static class InnerClass {
    public interface InnerInterface<t> { // warn
    }
  }
}
