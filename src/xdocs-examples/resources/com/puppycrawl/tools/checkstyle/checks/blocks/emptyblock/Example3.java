/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyBlock">
      <property name="tokens" value="LITERAL_CASE,LITERAL_DEFAULT"/>
    </module>
  </module>
</module>
*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

// xdoc section -- start
public class Example3 {
  private void emptyLoop() {
    for (int i = 0; i < 10; i++) {

    }

    try {
    }
    catch (Exception e) {
      // ok, LITERAL_CATCH is not part of the tokens property.
    }
  }

  private void testBadSwitchStatement(int a) {
    switch (a) {
      case 1: { }    // violation 'Must have at least one statement'
      case 2: {} {}; // violation 'Must have at least one statement'
      case 3: {} { System.out.println(); }
      // violation above 'Must have at least one statement'
      case 4: { System.out.println(); } {}
      default: { }   // violation 'Must have at least one statement'
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
      case 1 -> { }  // violation 'Must have at least one statement'
      default -> { } // violation 'Must have at least one statement'
    }
  }

  void someMethod() { }
}
// xdoc section -- end
