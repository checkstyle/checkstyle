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
    super(arg); // ok, explicit constructor invocation with argument
  }

  Example1(long arg) {
    // ok, no explicit constructor invocation
  }
}
// xdoc section -- end
