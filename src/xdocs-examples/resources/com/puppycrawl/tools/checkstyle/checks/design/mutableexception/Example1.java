/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MutableException"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.design.mutableexception;

// xdoc section -- start
class Example1 extends Exception {
    private int code; // OK, class name doesn't match with default pattern

    public Example1() {
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
    final int code; // OK
    String message; // violation

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
