/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoLineWrap">
      <property name="tokens" value="CLASS_DEF, METHOD_DEF, CTOR_DEF"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.      // ok, PACKAGE_DEF is not part of the tokens
  tools.checkstyle.checks.whitespace.nolinewrap;

import com.puppycrawl.tools. // ok, IMPORT is not part of the tokens
  checkstyle.api.AbstractCheck;

import static java.math.     // ok, STATIC_IMPORT is not part of the tokens
  BigInteger.ZERO;

class                        // violation 'should not be line-wrapped'
  Example3 {

  public                     // violation 'should not be line-wrapped'
    Example3() {}
  public void                // violation 'should not be line-wrapped'
    doSomething() {}
}
// xdoc section -- end
