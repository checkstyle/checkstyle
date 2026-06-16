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

    }

    try {
    } // violation above 'Empty try block'
    catch (Exception e) {
      // ok, LITERAL_CATCH is not part of the tokens property.
    }
  }

  private void testBadSwitchStatement(int a) {
    switch (a) {
      case 1: { }
      case 2: {} {};
      case 3: {} { System.out.println(); }

      case 4: { System.out.println(); } {}
      default: { }
    }
  }

  private void testGoodSwitchStatement(int a) {
    switch (a) {
      case 1: { someMethod(); }
      default: // ok, as there is no block
    }
  }

  private void testBadSwitchRule(int a) {
    switch (a) {
      case 1 -> { }
      default -> { }
    }
  }

  void someMethod() { }
}
// xdoc section -- end
