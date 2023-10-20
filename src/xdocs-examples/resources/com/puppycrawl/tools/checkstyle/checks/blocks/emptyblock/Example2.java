/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyBlock">
      <property name="option" value="text"/>
      <property name="tokens" value="LITERAL_TRY"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

// xdoc section -- start
public class Example2 {
  private void emptyLoop() {
    for (int i = 0; i < 10; i++) {
      // ignored
    }

    try {
    }  // violation above 'Empty try block'
    catch (Exception e) {
      // ignored
    }
  }
}
// xdoc section -- end
