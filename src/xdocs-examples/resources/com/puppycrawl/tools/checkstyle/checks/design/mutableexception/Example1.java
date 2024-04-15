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

class FirstMyException extends Exception {
    private int code; // violation

    public FirstMyException() {
        code = 2;
    }
}

class FirstMyThrowable extends Throwable {
    final int code; // OK
    String message; // violation

    public FirstMyThrowable(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

class FirstBadException extends java.lang.Exception {
    int code; // violation

    public FirstBadException(int code) {
        this.code = code;
    }
}
// xdoc section -- end
