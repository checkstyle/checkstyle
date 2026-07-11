/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalTokenText">
        <property name="tokens" value="TEXT_BLOCK_CONTENT"/>
        <property name="format" value='"'/>
    </module>
  </module>
</module>
*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;

// xdoc section -- start
public class UseCase1 {
  public void myTest() {

    String test  = "a href";

    String test2 = "A href";
    String link = "href";
    final String quote = """
            \""""; // violation above 'Token text matches the illegal pattern '"'.'
    int num1 = 0;
    int num2 = 0x111;
    int num3 = 0X111;
    int num4 = 010;
    long num5 = 0L;
    long num6 = 010L;
  }
}
// xdoc section -- end
