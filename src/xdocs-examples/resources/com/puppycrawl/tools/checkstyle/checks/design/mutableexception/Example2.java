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

class MyException extends Exception {
    private int code; // violation

    public MyException() {
        code = 2;
    }
}

class MyThrowable extends Throwable {
    final int code; // OK, class name doesn't match with given pattern
    String message; // OK, class name doesn't match with given pattern

    public MyThrowable(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

class BadException extends java.lang.Exception {
    int code; // violation

    public BadException(int code) {
        this.code = code;
    }
}
// xdoc section -- end
