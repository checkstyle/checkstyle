/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MutableException">
      <property name="extendedClassNameFormat" value="^.*Throwable$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.mutableexception;

// xdoc section -- start
class Example3 extends Exception {
  private int code; // OK, extended class name doesn't match with given pattern

  public Example3() {
    code = 1;
  }
}

class ThirdMyException extends Exception {
  private int code; // OK, extended class name doesn't match with given pattern

  public ThirdMyException() {
    code = 2;
  }
}

class ThirdMyThrowable extends Throwable {
  final int code; // OK
  String message; // violation

  public ThirdMyThrowable(int code, String message) {
    this.code = code;
    this.message = message;
  }
}

class ThirdBadException extends java.lang.Exception {
  int code; // OK, extended class name doesn't match with given pattern

  public ThirdBadException(int code) {
    this.code = code;
  }
}
// xdoc section -- end
