/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantThis">
      <property name="allowAdjacentToRequiredThis" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

// xdoc section - start
public class Example3 {
  private int age;

  public void setAge(int value) {
    this.age = value;
    // violation above, 'Redundant "this", field 'age' can be accessed directly.'
  }

  public void process() {
    this.show();

  }
  public void show() {}

  private int x;
  private int y;
  private int z;

  public Example3(int x, int y) {
    this.x = x;
    this.y = y;
    this.z = 3;
    // violation above, 'Redundant "this", field 'z' can be accessed directly.'
  }
}
// xdoc section - end
