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
//non-compiled with javac: Compilable with Java14

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;

public class Example3 {
  public void myTest() {
    final String quote = """
            \""""; // violation above
  }
}
// xdoc section -- end
