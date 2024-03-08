/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidNoArgumentSuperConstructorCall"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.avoidnoargumentsuperconstructorcall;

// xdoc section -- start
class SuperClass {
  public SuperClass() {}

  public SuperClass(int arg) {}
}
class Example1 extends SuperClass {
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
