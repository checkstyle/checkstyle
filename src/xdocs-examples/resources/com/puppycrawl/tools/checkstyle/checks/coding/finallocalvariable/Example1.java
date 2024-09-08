/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalLocalVariable"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

// xdoc section -- start
class Example1 {
  public void foo() {
    int x = 1;
    int y = 2; // violation, 'Variable 'y' should be declared final'
    int z = 3; // violation, 'Variable 'z' should be declared final'
    x = z;
  }
}
// xdoc section -- end
