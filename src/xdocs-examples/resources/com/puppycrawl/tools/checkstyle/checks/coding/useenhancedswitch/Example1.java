/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UseEnhancedSwitch"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.useenhancedswitch;

// xdoc section -- start
public class Example1 {
  void t1(int x)  {
    // violation below, 'Switch can be replaced with enhanced switch'
    switch (x) {
      case 1: {} break;
      case 2: {} break;
      case 3: {} break;
    }
    // ok, switch with fall through is ignored
    switch (x) {
      case 1: {}
      case 2: {} break;
      case 3: {} break;
    }
    // ok, switch already enhanced
    switch (x) {
      case 1 -> {}
      case 2 -> {}
      case 3 -> {}
    }
  }

  int t2(int x) {
    // violation below, 'Switch can be replaced with enhanced switch'
    int y =  switch (x) {
      case 1 : yield 1;
      case 2 : yield 2;
      case 3 : yield 3;
      default: yield 0;
    };
    // ok, switch already enhanced
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
