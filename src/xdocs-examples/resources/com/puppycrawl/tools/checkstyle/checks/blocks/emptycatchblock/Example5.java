/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyCatchBlock">
      <property name="exceptionVariableName" value="myException"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptycatchblock;

// xdoc section -- start
public class Example5 {
  private void exampleMethod1() {
    try {
      throw new RuntimeException();
    } catch (RuntimeException e) {
    } // violation above
  }

  private void exampleMethod2() {
    try {
      throw new RuntimeException();
    } catch (RuntimeException myException) {
    }
  }
}
// xdoc section -- end
