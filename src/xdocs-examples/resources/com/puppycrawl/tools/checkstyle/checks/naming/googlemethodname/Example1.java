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
  public void parseXml() {}

  public void Foo() {}
  // violation above, 'Method name 'Foo' must start with a lowercase letter'

  public void gradle_9_5_1() {}
  // violation above, 'underscores only allowed between adjacent digits.'

  @Test
  public void login() {}

  @Test
  public void jdk17_0_1() {}

  @Test
  public void testLogin_failsGracefully12() {}

  @Test
  public void transferMoney_deductsFromSource() {}

  @Test
  public void login_fails() {}
  // violation above, 'contain at least one uppercase letter for multi-segment names'

  @Test
  public void test_foo_1() {}
  // violation above, 'underscore only allowed between letters or between digits.'

  @Test
  public void f$bar() {}
  // violation above, 'contain only letters, digits, or underscores.'

  @Test
  public void test_Foo() {}
  // violation above, 'Each segment must start lowercase'
}
// xdoc section -- end
