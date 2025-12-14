/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GoogleMethodName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

import org.junit.jupiter.api.Test;

// xdoc section -- start
class Example1 {
  public void fooBar() {}
  public void guava33_4_5() {}
  public void Foo() {}
  // violation above, 'Method name 'Foo' is not valid per Google Java Style Guide'

  public void gradle_9_5_1() {}
  // violation above, ''gradle_9_5_1' is not valid per Google Java Style Guide.'

  @Test
  public void login_fails() {}

  @Test
  public void login_Fails() {}
  // violation above, ''login_Fails' is not valid per Google Java Style Guide.'
}
// xdoc section -- end
