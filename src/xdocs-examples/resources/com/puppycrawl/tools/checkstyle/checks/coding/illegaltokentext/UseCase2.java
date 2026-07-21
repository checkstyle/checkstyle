/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalTokenText">
      <property name="tokens" value="NUM_INT,NUM_LONG"/>
      <property name="format" value="^0[^lx]"/>
      <property name="ignoreCase" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;

// xdoc section -- start
public class UseCase2 {
  public void myTest() {

    String test  = "a href";

    String test2 = "A href";
    String link = "href";
    final String quote = """
            \"""";
    int num1 = 0;
    int num2 = 0x111;
    int num3 = 0X111; // ok, case is ignored
    int num4 = 010;     // violation 'Token text matches the illegal pattern'
    long num5 = 0L;
    long num6 = 010L;   // violation 'Token text matches the illegal pattern'
  }
}
// xdoc section -- end
