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
  public void test() {
    String link  = "href"; // violation, Custom illegal text found
  }
}
// xdoc section -- end
