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
  // violation above, 'Method name 'Foo' must start with a lowercase letter, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  public void gradle_9_5_1() {}
  // violation above, 'Method name 'gradle_9_5_1' has invalid underscore usage, underscores only allowed between adjacent digits.'

  @Test
  public void login() {}

  @Test
  public void jdk17_0_1() {}

  @Test
  public void testLogin_failsGracefully() {}

  @Test
  public void transferMoney_deductsFromSource() {}

  @Test
  public void login_fails() {}
  // violation above, 'Test method name 'login_fails' is not valid. Each segment must start lowercase, contain at least one uppercase letter for multi-segment names, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  @Test
  public void testLogin_fails() {}
  // violation above, 'Test method name 'testLogin_fails' is not valid. Each segment must start lowercase, contain at least one uppercase letter for multi-segment names, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  @Test
  public void test_foo_1() {}
  // violation above, 'Test method name 'test_foo_1' has invalid underscore usage, underscore only allowed between letters or between digits.'

  @Test
  public void f$bar() {}
  // violation above, 'Test method name 'f\$bar' is not valid. Each segment must start lowercase, contain at least one uppercase letter for multi-segment names, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'

  @Test
  public void test_Foo() {}
  // violation above, 'Test method name 'test_Foo' is not valid. Each segment must start lowercase, contain at least one uppercase letter for multi-segment names, min 2 chars, avoid single lowercase letter followed by uppercase, and contain only letters, digits, or underscores.'
}
// xdoc section -- end
