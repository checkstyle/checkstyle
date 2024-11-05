/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalToken"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltoken;

// xdoc section -- start
class Example1 {
  void InvalidExample() {
    outer: // violation, 'Using 'outer:' is not allowed'
    for (int i = 0; i < 5; i++) {
      if (i == 1) {
        break outer;
      }
    }
  }
}
// xdoc section -- end
