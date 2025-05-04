/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DeclarationOrder">
      <property name="ignoreConstructors" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

// xdoc section -- start
public class Example2 {

  public int a;
  protected int b;
  public int c;            // violation, variable access definition in wrong order

  Example2() {
    this.a = 0;
  }

  public void foo() {
    // This method does nothing
  }

  Example2(int a) {            // ok, validation of constructors ignored
    this.a = a;
  }

  private String name;     // violation, instance variable declaration in wrong order
}
// xdoc section -- end
