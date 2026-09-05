/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantThis">
      <property name="checkMethods" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

// xdoc section - start
public class Example2 {
  private String name;
  private int age;

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int value) {
    this.age = value;
    // violation above, 'Redundant "this", field 'age' can be accessed directly.'
  }

  public void process() {
    this.show();
    // violation above, 'Redundant "this", method 'show' can be accessed directly.'
  }
  public void show() {}

  public boolean validThis() {
    return this instanceof Object;
  }

  enum MyEnum {
    A, B;
    private String name;
    public void printName() {
      System.out.println(this.name);
      // violation above """Redundant "this", field 'name' can be
      // accessed directly."""
    }
  }

  interface MyInterface {
    default void getSomething() {
      this.get();
      // violation above 'Redundant "this", method 'get' can be accessed directly.'
    }
    default void get() {}
  }
}
// xdoc section - end
