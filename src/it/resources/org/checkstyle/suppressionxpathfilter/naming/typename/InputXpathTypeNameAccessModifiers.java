package org.checkstyle.suppressionxpathfilter.naming.typename;

public class InputXpathTypeNameAccessModifiers {

  public interface FirstName {}
  public class SecondName {
    protected class ThirdName {}  // warn
  }
}
