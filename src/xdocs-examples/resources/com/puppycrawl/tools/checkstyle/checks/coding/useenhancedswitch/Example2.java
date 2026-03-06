/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UseEnhancedSwitch"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.useenhancedswitch;

// xdoc section -- start
public class Example2 {
  int switchExpressions(int x) {
    // violation below, 'Switch can be replaced with enhanced switch'
    int y = switch (x) {
      case 1 : yield 1;
      case 2 : yield 2;
      case 3 : yield 3;
      default: yield 0;
    };
    // ok, same as above but using enhanced switch
    int yEnhanced = switch (x) {
      case 1 -> 1;
      case 2 -> 2;
      case 3 -> 3;
      default -> 0;
    };
    return y;
  }
}
// xdoc section -- end
