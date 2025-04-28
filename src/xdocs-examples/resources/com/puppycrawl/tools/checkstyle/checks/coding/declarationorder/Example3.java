/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DeclarationOrder">
      <property name="ignoreModifiers" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

// xdoc section -- start
public class Example3 {

  public int a;
  protected int b;
  public int c;            // ok, access modifiers not considered while validating

  Example3() {
    this.a = 0;
  }

  public void foo() {
    // This method does nothing
  }

  Example3(int a) {            // violation, constructor definition in wrong order
    this.a = a;
  }

  private String name;     // violation, instance variable declaration in wrong order
}
// xdoc section -- end
