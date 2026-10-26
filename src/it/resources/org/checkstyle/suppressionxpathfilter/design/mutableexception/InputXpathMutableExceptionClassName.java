/*
MutableException
extendedClassNameFormat = (default)^.*Exception$|^.*Error$|^.*Throwable$
format = ^.*ExceptionClassName$


*/

package org.checkstyle.suppressionxpathfilter.design.mutableexception;

public class InputXpathMutableExceptionClassName extends java.lang.Exception {
  // violation below 'The field 'code' must be declared final.'
  int code;

  public InputXpathMutableExceptionClassName(int code) {
    this.code = code;
  }
}
