/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyCatchBlock">
      <property name="commentFormat" value="This is expected"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptycatchblock;
// xdoc section -- start
public class Example4 {
  private void exampleMethod1() {
    try {
      throw new RuntimeException();
    } catch (RuntimeException e) {
      //This is expected
    }
  }

  private void exampleMethod2() {
    try {
      throw new RuntimeException();
    } catch (RuntimeException e) {
      //   This is expected
    }
  }

  private void exampleMethod3 () {
    try {
      throw new RuntimeException();
    } catch (RuntimeException e) {
      // This is expected
      // some another comment
    }
  }

  private void exampleMethod4 () {
    try {
      throw new RuntimeException();
    } catch (RuntimeException e) {
      /* This is expected */
    }
  }

  private void exampleMethod5() {
    try {
      throw new RuntimeException();
      // violation below
    } catch (RuntimeException e) {
      /*
       *
       * This is expected
       * some another comment */
    }
  }
}
// xdoc section -- end
