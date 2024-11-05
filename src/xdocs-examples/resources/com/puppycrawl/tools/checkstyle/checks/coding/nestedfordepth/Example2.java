/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NestedForDepth">
      <property name="max" value="2"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.nestedfordepth;

// xdoc section -- start
public class Example2 {

  public void myTest() {
    for(int i=0; i<10; i++) {
      for(int j=0; j<i; j++) {
      }
    }

    for(int i=0; i<10; i++) {
      for(int j=0; j<i; j++) {
        for(int k=0; k<j; k++) {
        }
      }
    }

    // violation 4 lines below 'Nested for depth is 3 (max allowed is 2).'
    for(int i=0; i<10; i++) {
      for(int j=0; j<i; j++) {
        for(int k=0; k<j; k++) {
          for(int l=0; l<k; l++) {
          }
        }
      }
    }
  }
}
// xdoc section -- end
