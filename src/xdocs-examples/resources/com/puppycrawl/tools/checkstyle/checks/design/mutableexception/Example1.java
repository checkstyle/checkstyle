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

class MyException1 extends Exception {
    private int code; // violation, The field 'code' must be declared final

    public MyException1() {
        code = 2;
    }
}

class MyThrowable1 extends Throwable {
    final int code; // OK
    String message; // violation, The field 'message' must be declared final

    public MyThrowable1(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

class BadException1 extends java.lang.Exception {
    int code; // violation, The field 'code' must be declared final

    public BadException1(int code) {
        this.code = code;
    }
}
// xdoc section -- end
