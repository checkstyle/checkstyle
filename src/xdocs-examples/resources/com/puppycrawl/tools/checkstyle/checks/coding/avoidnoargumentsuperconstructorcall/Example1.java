/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidNoArgumentSuperConstructorCall"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.avoidnoargumentsuperconstructorcall;

class SomeOtherClass {
  public SomeOtherClass() {}

  public SomeOtherClass(int arg) {}
}


// xdoc section -- start
class Example1 extends SomeOtherClass {
  Example1() {
    super(); // violation
  }

  Example1(int arg) {
    super(arg); // OK, call with argument have to be explicit
  }

  Example1(long arg) {
    // OK, call is implicit
  }
}
// xdoc section -- end

