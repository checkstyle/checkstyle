package org.checkstyle.suppressionxpathfilter.naming.interfacetypeparametername;

public class InputXpathInterfaceTypeParameterNameInsideInnerClass {
  public static class InnerClass {
    public interface InnerInterface<t> { // warn
    }
  }
}
