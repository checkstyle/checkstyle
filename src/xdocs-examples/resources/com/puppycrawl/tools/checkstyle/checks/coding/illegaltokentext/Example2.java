package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;
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

// xdoc section -- start
public class Example2 {
    public void myTest() {
        String test = "a href"; // violation
        String test2 = "A href"; // violation, case is ignored
    }
}
// xdoc section -- end
