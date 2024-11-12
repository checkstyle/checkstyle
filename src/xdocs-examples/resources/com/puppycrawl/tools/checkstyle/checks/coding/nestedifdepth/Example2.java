/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NestedIfDepth">
      <property name="max" value="3"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.nestedifdepth;

// xdoc section -- start
class Example2 {
  void Example2() {
    if (true) {
      if (true) {}
      else {}
    }

    if (true) {
      if (true) {
        if (true) {}
        else{}
      }
    }

    if (true) {
      if (true) {
        if (true) {
          if (true) {}
          else {}
        }
      }
    }

    if (true) {
      if (true) {
        if (true) {
          if (true) {
            if (true) { // violation, nested if-else depth is 4 (max allowed is 3)
              if (true) {} // violation, nested if-else depth is 5 (max allowed is 3)
              else {}
            }
          }
        }
      }
    }
  }
}
// xdoc section -- end
