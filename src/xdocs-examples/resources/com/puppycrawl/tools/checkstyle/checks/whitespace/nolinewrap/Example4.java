/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoLineWrap">
      <property name="tokens" value="IMPORT, STATIC_IMPORT,
      PACKAGE_DEF, CLASS_DEF, METHOD_DEF, CTOR_DEF"/>
      <property name="skipAnnotations" value="false"/>
    </module>
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
  Example4 {

  public                     // violation 'should not be line-wrapped'
    Example4() {}
  public void                // violation 'should not be line-wrapped'
    doSomething() {}
  @Deprecated                // violation 'should not be line-wrapped'
  private void doNothing() {}
}
// xdoc section -- end
