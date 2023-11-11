/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FallThrough"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

// xdoc section -- start
class Example1 {
  public void foo() throws Exception {
    int i = 0;
    while (i >= 0) {
      switch (i) {
        case 1:
          i++;
            /* block */ /* fallthru */ // comment
        case 2: // ok, ReliefPattern is present in above line.
          i++;
          break;
        case 3:
          i++;
          return;
        case 4:
          i++;
          throw new Exception();
        case 5:
          i++; // no break by design
        case 6: // violation 'Fall\ through from previous branch of the switch'
        case 7:
          i++;
          continue;
        case 11:
          i++;
      }
    }
  }
}
// xdoc section -- end
