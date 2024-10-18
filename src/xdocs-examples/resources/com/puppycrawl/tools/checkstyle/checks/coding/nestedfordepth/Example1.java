/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NestedForDepth"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.nestedfordepth;

// xdoc section -- start
public class Example1 {
  public void myTest() {
    for(int i=0; i<10; i++) {
      for(int j=0; j<i; j++) {
        // violation below, 'Nested for depth is 2 (max allowed is 1).'
        for(int k=0; k<j; k++) {
        }
      }
    }

    for(int i=0; i<10; i++) {
      for(int j=0; j<i; j++) {
      }
    }
  }
}
// xdoc section -- end
