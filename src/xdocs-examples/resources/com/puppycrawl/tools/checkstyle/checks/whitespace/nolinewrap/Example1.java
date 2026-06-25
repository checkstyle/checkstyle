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

class                        // violation 'should not be line-wrapped'
  Example1 {

  public                     // violation 'should not be line-wrapped'
    Example1() {}
  public void                // violation 'should not be line-wrapped'
    doSomething() {}
}
// xdoc section -- end
