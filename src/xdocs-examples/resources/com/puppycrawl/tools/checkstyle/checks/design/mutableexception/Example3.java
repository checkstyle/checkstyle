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
class ThirdClass extends Exception {
    private int code; // OK, extended class name doesn't match with given pattern

    public ThirdClass() {
        code = 1;
    }
}

class MyException3 extends Exception {
    private int code; // OK, extended class name doesn't match with given pattern

    public MyException3() {
        code = 2;
    }
}

class MyThrowable3 extends Throwable {
    final int code; // OK
    String message; // violation, The field 'message' must be declared final

    public MyThrowable3(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

class BadException3 extends java.lang.Exception {
    int code; // OK, extended class name doesn't match with given pattern

    public BadException3(int code) {
        this.code = code;
    }
}
// xdoc section -- end
