/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DeclarationOrder"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

// xdoc section - start
public class Example1 {

  public int a;
  protected int b;
  public int c;         // violation 'Variable access definition in wrong order.'

  Example1() {
    this.a = 0;
  }

  public void foo() {
    // This method does nothing
  }

  Example1(int a) {     // violation 'Constructor definition in wrong order.'
    this.a = a;
  }

  private String name;  // violation 'Instance variable definition in wrong order.'
}
// xdoc section - end
