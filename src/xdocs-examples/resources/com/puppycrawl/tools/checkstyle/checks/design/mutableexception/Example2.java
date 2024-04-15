/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MutableException">
      <property name="format" value="^.*Exception$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.mutableexception;

// xdoc section -- start
class Example2 extends Exception {
    private int code; // OK, class name doesn't match with given pattern

    public Example2() {
        code = 1;
    }
}

class SecondMyException extends Exception {
    private int code; // violation

    public SecondMyException() {
        code = 2;
    }
}

class SecondMyThrowable extends Throwable {
    final int code; // OK, class name doesn't match with given pattern
    String message; // OK, class name doesn't match with given pattern

    public SecondMyThrowable(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

class SecondBadException extends java.lang.Exception {
    int code; // violation

    public SecondBadException(int code) {
        this.code = code;
    }
}
// xdoc section -- end
