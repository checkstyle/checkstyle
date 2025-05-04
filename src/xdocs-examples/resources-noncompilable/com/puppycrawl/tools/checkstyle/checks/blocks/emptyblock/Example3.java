/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyBlock">
      <property name="tokens" value="LITERAL_CASE,LITERAL_DEFAULT"/>
    </module>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

// xdoc section -- start
public class Example3 {

  private void testBadSwitchStatement(int a) {
    switch (a) {
      case 1 : { }  // violation, 'Must have at least one statement'
      // the second empty block is 'sequential', skipped.
      // violation below, 'Must have at least one statement'
      case 2: {} {};
      // violation below, 'Must have at least one statement'
      case 3: {} {System.out.println();}
      // the second empty block is 'sequential', skipped.
      case 4: {System.out.println();} {}
      default : { }  // violation, 'Must have at least one statement'
    }
  }

  private void testGoodSwitchStatement(int a) {
    switch (a) {
      case 1: { someMethod(); }
      default: { someMethod(); }
    }
    switch (a) {
      case 1: { someMethod(); }
      default: // ok, as there is no block
    }
  }

  private void testBadSwitchRule(int a) {
    switch (a) {
      case 1 -> { } // violation, 'Must have at least one statement'
      default -> { } // violation, 'Must have at least one statement'
    }
  }

  private void testGoodSwitchRule(int a) {
    switch (a) {
      case 1 -> { someMethod(); }
      default -> { someMethod(); }
    }
  }

  void someMethod() { }
}
// xdoc section -- end
