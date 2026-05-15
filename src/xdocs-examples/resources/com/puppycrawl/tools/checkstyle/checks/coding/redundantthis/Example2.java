/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantThis"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

// xdoc section -- start
public class Example2 {
  private String name;
  private int age;

  public void setName(String value) {
    this.name = name;
    // violation above, 'Redundant "this", variable 'name' can be accessed directly.'
  }

  public void setAge(int value) {
    this.age = value;
    // violation above, 'Redundant "this", variable 'age' can be accessed directly.'
  }

  public void process() {
    this.show(); // ok, checkMethodCall is false by default
  }

  public String show() {
    return name + " " + age;
  }
}
// xdoc section -- end
