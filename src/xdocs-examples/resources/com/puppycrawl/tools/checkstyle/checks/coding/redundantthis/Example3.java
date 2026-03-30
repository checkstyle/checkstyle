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
public class Example3 {
  private String name;
  private int age;

  public void setName(String value) {
    this.name = name;
    // violation above, 'Redundant "this", variable 'name' can be accesed directly.'
  }

  public void setAge(int value) {
    this.age = value;
    // violation above, 'Redundant "this", variable 'age' can be accesed directly.'
  }

  public void process() {
    this.show();
    // violation above, 'Redundant "this", method 'show' can be accesed directly.'
  }

  public String show() {
    return name + " " + age;
  }
}
// xdoc section -- end
