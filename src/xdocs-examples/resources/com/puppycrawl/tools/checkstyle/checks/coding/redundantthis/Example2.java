/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantThis">
      <property name="checkMethodCall" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

// xdoc section -- start
public class Example2 {
  private String name;
  private int age;

  public void setName(String name) {
    this.name = name;   // ok, "this" is required here
  }

  public void setAge(int value) {
    this.age = value;
    // violation above, 'Redundant "this", variable 'age' can be accessed directly.'
  }

  public void process() {
    this.show();
    // violation above, 'Redundant "this", method 'show' can be accessed directly.'
  }

  public String show() {
    return name + " " + age;
  }
}
// xdoc section -- end
