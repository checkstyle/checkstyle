/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyBlock"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

// xdoc section -- start
public class Example1 {
  private void emptyLoop() {
    for (int i = 0; i < 10; i++) { // violation 'Must have at least one statement'
    }

    try { // violation 'Must have at least one statement'

    } catch (Exception e) {
      // ignored
    }
  }
}
// xdoc section -- end
