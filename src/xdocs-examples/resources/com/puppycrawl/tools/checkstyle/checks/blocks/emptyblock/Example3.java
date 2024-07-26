/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyBlock">
            <property name="tokens" value="LITERAL_DEFAULT, LITERAL_CASE"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

// xdoc section -- start
public class Example3 {
  void m1(int a) {
    switch (a) {
      // No violations below, the second empty block is 'sequential'.
      // violation below, 'Must have at least one statement'
      case 1: {} {};
      // violation below, 'Must have at least one statement'
      case 2: {} {System.out.println();}
      // No violations below, the second empty block is 'sequential'.
      case 3: {System.out.println();} {}
    }
  }

  void m2(int a) {
    switch (a) {
      // violation below, 'Must have at least one statement'
      default: {} {System.out.println();}
    }
  }

  void m3(int a) {
    switch (a) {
      // No violations below, the second empty block is 'sequential'.
      default: {System.out.println();} {}
    }
  }
}
// xdoc section -- end
