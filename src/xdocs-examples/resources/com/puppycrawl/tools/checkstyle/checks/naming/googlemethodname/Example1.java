/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GoogleMethodName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

// xdoc section -- start
import org.junit.jupiter.api.Test;

class Example1 {
  public void fooBar() {}
  public void guava33_4_5() {}
  public void parseXml() {}

  public void Foo() {}
  // violation above """Method name 'Foo' must be more than a character,
  // start lowercase, and not have a single lowercase followed by uppercase."""

  public void gradle_9_5_1() {}
  // violation above """Method name 'gradle_9_5_1' has invalid underscore usage,
  // underscores only allowed between adjacent digits."""

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

  @Test
  public void test_1() {}
  // violation above """Test method name 'test_1' has invalid underscore usage,
  // underscore only allowed between letters or between digits."""

  @Test
  public void test_Foo() {}
  // violation above """Test method name 'test_Foo' segment must be more than a
  // character, start lowercase, and not have a single lowercase followed
  // by uppercase."""
}
// xdoc section -- end
