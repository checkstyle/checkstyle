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

    int lower, higher; // violation, multiple variables declared in the same statement

    int value, // violation, multiple variables declared in the same statement
        index;

    int place = mid, number = high;  // violation, multiple variables declared in the same statement
  }
}
// xdoc section -- end
