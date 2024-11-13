/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NestedIfDepth"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.nestedifdepth;

// xdoc section -- start
class Example1 {
  void Example1() {
    if (true) {
      if (true) {}
      else {}
    }

    if (true) {
      if (true) {
        if (true) {} // violation, nested if-else depth is 2 (max allowed is 1)
        else{}
      }
    }

    if (true) {
      if (true) {
        if (true) { // violation, nested if-else depth is 2 (max allowed is 1)
          if (true) {} // violation, nested if-else depth is 2 (max allowed is 1)
          else {}
        }
      }
    }

    if (true) {
      if (true) {
        if (true) { // violation, nested if-else depth is 2 (max allowed is 1)
          if (true) { // violation, nested if-else depth is 2 (max allowed is 1)
            if (true) { // violation, nested if-else depth is 4 (max allowed is 1)
              if (true) {} // violation, nested if-else depth is 5 (max allowed is 1)
              else {}
            }
          }
        }
      }
    }
  }
}
// xdoc section -- end
