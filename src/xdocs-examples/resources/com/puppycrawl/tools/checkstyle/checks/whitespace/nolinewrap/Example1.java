/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoLineWrap"/>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.      // violation 'should not be line-wrapped'
  tools.checkstyle.checks.whitespace.nolinewrap;

import com.puppycrawl.tools. // violation 'should not be line-wrapped'
  checkstyle.api.AbstractCheck;

import static java.math.     // violation 'should not be line-wrapped'
  BigInteger.ZERO;

class
  Example1 {

  public
    Example1() {}
  public void
    doSomething() {}
  @Deprecated
  private void doNothing() {}
}
// xdoc section -- end
