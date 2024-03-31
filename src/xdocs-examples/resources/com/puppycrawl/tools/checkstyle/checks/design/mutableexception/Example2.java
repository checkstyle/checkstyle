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
class SecondClass extends Exception {
    private int code; // OK, class name doesn't match with given pattern

    public SecondClass() {
        code = 1;
    }
}

class MyException2 extends Exception {
    private int code; // violation, The field 'code' must be declared final

    public MyException2() {
        code = 2;
    }
}

class MyThrowable2 extends Throwable {
    final int code; // OK, class name doesn't match with given pattern
    String message; // OK, class name doesn't match with given pattern

    public MyThrowable2(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

class BadException2 extends java.lang.Exception {
    int code; // violation, The field 'code' must be declared final

    public BadException2(int code) {
        this.code = code;
    }
}
// xdoc section -- end
