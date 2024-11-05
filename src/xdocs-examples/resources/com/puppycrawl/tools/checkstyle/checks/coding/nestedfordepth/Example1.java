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
      }
    }

    // violation 3 lines below 'Nested for depth is 2 (max allowed is 1).'
    for(int i=0; i<10; i++) {
      for(int j=0; j<i; j++) {
        for(int k=0; k<j; k++) {
        }
      }
    }

    // violation 4 lines below 'Nested for depth is 2 (max allowed is 1).'
    // violation 4 lines below 'Nested for depth is 3 (max allowed is 1).'
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
