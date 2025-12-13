/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalTokenText">
      <property name="tokens" value="STRING_LITERAL"/>
      <property name="format" value="a href"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;

// xdoc section -- start
public class Example1 {
  public void myTest() {
    String test  =
      "a href"; // violation 'Token text matches the illegal pattern .a href.'
    String test2 = "A href"; // ok, case is sensitive
  }
}
// xdoc section -- end
