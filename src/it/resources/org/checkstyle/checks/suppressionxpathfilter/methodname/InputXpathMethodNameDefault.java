package org.checkstyle.checks.suppressionxpathfilter.methodname;

public class InputXpathMethodNameDefault {

  protected void firstMethod() {} // OK
  private void SecondMethod() {} // warn

}
