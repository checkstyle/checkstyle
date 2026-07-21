/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalTokenText">
      <property name="tokens" value="STRING_LITERAL"/>
      <property name="format" value="href"/>
      <property name="message" value="Custom illegal text found"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;

// xdoc section -- start
public class Example5 {
  public void myTest() {

    String test  = "a href"; // violation 'Custom illegal text found'

    String test2 = "A href"; // violation 'Custom illegal text found'
    String link = "href";    // violation 'Custom illegal text found'
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
