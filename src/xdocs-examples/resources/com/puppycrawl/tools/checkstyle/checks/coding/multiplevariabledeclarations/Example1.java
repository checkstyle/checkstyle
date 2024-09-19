/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MultipleVariableDeclarations"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.multiplevariabledeclarations;

// xdoc section -- start
public class Example1 {
  public void myTest() {
    int mid = 0;
    int high = 0;

    int lower, higher;
    // violation above, 'Each variable declaration must be in its own statement'

    int value, // violation, 'Each variable declaration must be in its own statement'
        index;
    int place = mid, number = high;
    // violation above, 'Each variable declaration must be in its own statement'
  }
}
// xdoc section -- end
