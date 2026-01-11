/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoLineWrap">
      <property name="tokens" value="CLASS_DEF, METHOD_DEF, CTOR_DEF"/>
      <property name="skipAnnotations" value="false"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

// xdoc section -- start
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import static java.math.BigInteger.ZERO;

class // violation 'should not be line-wrapped'
  Example6 {

  @Deprecated // violation 'should not be line-wrapped'
  public Example6() {
  }

  public static void // violation 'should not be line-wrapped'
    doSomething() {
  }

  class Bar {

    public // violation 'should not be line-wrapped'
      Bar() {
    }

    @Deprecated // violation 'should not be line-wrapped'
    public void fun() {
    }

    @Deprecated // violation 'should not be line-wrapped'
    public void
      method() {}
  }
}
// xdoc section -- end
