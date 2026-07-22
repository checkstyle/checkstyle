/*xml
  <module name="Checker">
    <module name="TreeWalker">
      <module name="UnusedPrivateField">
      </module>
    </module>
  </module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;
// xdoc section -- start
public class Example1 {

  @interface Getter {}

  private int unused; // violation, 'Unused private field'

  private int used;

  @Getter
  private int getter; // violation, 'Unused private field'

  private int getUsed(){
    return used;
  }

  private static final int CONSTANT = 10; // violation, 'Unused private field'

  void foo() {
    System.out.println(used); // ok, used in inner class
  }
}
// xdoc section -- end
