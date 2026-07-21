/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalTokenText">
      <property name="tokens" value="COMMENT_CONTENT"/>
      <property name="format" value="a href"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;
// xdoc section -- start
// violation first line 'Token text matches the illegal pattern 'a href''
public class Example3 { // violation above 'Token text matches the illegal pattern'
  public void myTest() {

    String test  = "a href";

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
