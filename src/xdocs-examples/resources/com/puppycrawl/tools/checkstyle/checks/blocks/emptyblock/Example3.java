/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyBlock">
      <property name="tokens" value="LITERAL_DEFAULT"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

import static com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier.InputRedundantModifierFinalInEnumStaticMethods.TestEnum.someMethod;

// xdoc section -- start
public class Example3 {
  private void test(int a) {
    switch (a) {
      case 1: someMethod();
      default: // OK, as there is no block
    }
    switch (a) {
      case 1: someMethod();
      default: {} // violation 'Must have at least one statement'
    }
  }
}
// xdoc section -- end
