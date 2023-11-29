/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyCatchBlock">
      <property name="exceptionVariableName" value="expected|ignore"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptycatchblock;

// xdoc section -- start
public class Example2 {
  private void exampleMethod1() {
    try {
      throw new RuntimeException();
    } catch (RuntimeException expected) {
    }
  }

  private void exampleMethod2() {
    try {
      throw new RuntimeException();
    } catch (RuntimeException ignore) {
      // no handling
    }
  }

  private void exampleMethod3 () {
    try {
      throw new RuntimeException();
    } catch (RuntimeException o) {
    } // violation above
  }

  private void exampleMethod4 () {
    try {
      throw new RuntimeException();
    } catch (RuntimeException ex) {
      // This is expected
    }
  }
}
// xdoc section -- end
