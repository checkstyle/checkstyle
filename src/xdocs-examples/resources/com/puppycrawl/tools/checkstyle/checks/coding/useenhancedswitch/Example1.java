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
  void doSomething(String param) {
    // method implementation
  }

  void t1(int x)  {
    // violation below, 'Switch can be replaced with enhanced switch'
    switch (x) {
      case 1:
        doSomething("one");
        break;
      case 2:
        doSomething("two");
        break;
      case 3:
        doSomething("three");
        break;
    }
    // ok, same as above but using enhanced switch
    switch (x) {
      case 1 -> doSomething("one");
      case 2 -> doSomething("two");
      case 3 -> doSomething("three");
    }
    // ok, switch with fall through is ignored
    switch (x) {
      case 1:
        doSomething("one");
      case 2:
        doSomething("one or two");
        break;
      case 3:
        doSomething("three");
        break;
    }
  }
}
// xdoc section -- end
