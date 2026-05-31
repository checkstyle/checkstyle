/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalTokenText">
      <property name="tokens" value="STRING_LITERAL"/>
      <property name="format" value="a href"/>
      <property name="ignoreCase" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;

// xdoc section -- start
public class Example2 {
  public void myTest() {
    // violation below 'Token text matches the illegal pattern 'a href'.'
    String test  = "a href";
    // violation below 'Token text matches the illegal pattern 'a href'.'
    String test2 = "A href";
    String link = "href";
    final String quote = """
            \"""";
    int num1 = 0;
    int num2 = 0x111;
    int num3 = 0X111;
    int num4 = 010;
    long num5 = 0L;
    long num6 = 010L;
  }
}
// xdoc section -- end
