/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoLineWrap">
      <property name="tokens" value="CLASS_DEF, METHOD_DEF, CTOR_DEF"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

// xdoc section -- start

class // violation 'should not be line-wrapped'
  Example3 {

  public Example3() {
  }

  public static void // violation 'should not be line-wrapped'
    doSomething() {
  }

  class Bar {

    public // violation 'should not be line-wrapped'
      Bar() {
    }

    public void fun() {
    }
  }
}
// xdoc section -- end
