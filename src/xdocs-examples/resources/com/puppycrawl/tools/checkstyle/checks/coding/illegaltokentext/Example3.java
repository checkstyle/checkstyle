package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;
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

// xdoc section -- start
public class Example3 {
    public void myTest() {
        final String quote = "           \"";  // violation
    }
}
// xdoc section -- end
