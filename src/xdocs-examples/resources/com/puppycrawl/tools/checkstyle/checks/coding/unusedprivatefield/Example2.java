/*xml
  <module name="Checker">
    <module name="TreeWalker">
      <module name="UnusedPrivateField">
          <property name="checkUnusedPrivateField" value="true"/>
      </module>
    </module>
  </module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;
// xdoc section -- start
public class Example2 {

  private int unused; // ok, as private field is value is false

  private int used;

  private int getUsed(){
    return used;
  }

  private static final int CONSTANT = 10; // ok, constant is used

  void foo() {
    System.out.println(used); // ok, used in inner class
  }
}
// xdoc section -- end
