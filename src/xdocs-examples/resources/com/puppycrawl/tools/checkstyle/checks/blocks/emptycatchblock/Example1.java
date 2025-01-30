/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyCatchBlock"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptycatchblock;

// xdoc section -- start
public class Example1 {
  private void exampleMethod1() {
    try {
      throw new RuntimeException();
    } catch (RuntimeException expected) {
    } // violation above 'Empty catch block'
  }

  private void exampleMethod2() {
    try {
      throw new RuntimeException();
    } catch (RuntimeException ignore) {
      // no handling
    } //catch block has comment
  }

  private void exampleMethod3 () {
    try {
      throw new RuntimeException();
    } catch (RuntimeException o) {
    } // violation above 'Empty catch block'
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
