/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoLineWrap">
      <property name="tokens" value="IMPORT"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.      // ok, PACKAGE_DEF is not part of the tokens
  tools.checkstyle.checks.whitespace.nolinewrap;

import com.puppycrawl.tools. // violation 'should not be line-wrapped'
  checkstyle.api.AbstractCheck;

import static java.math.     // ok, STATIC_IMPORT is not part of the tokens
  BigInteger.ZERO;

class
  Example2 {

  public
    Example2() {}
  public void
    doSomething() {}
  @Deprecated
  private void doNothing() {}
}
// xdoc section -- end
