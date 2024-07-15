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
      default: // OK, as there is no block
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
