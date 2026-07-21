/*xml
  <module name="Checker">
    <module name="TreeWalker">
      <module name="UnusedPrivateField">
      <property name="ignoreAnnotationCanonicalNames" value="Getter"/>
      </module>
    </module>
  </module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;
// xdoc section -- start
public class Example2 {

  @interface Getter {}

  private int unused; // violation, unused private field

  private int used;

  @Getter
  private int getter; // ok, as is supressed by property

  private int getUsed(){
    return used;
  }

  private static final int CONSTANT = 10; // ok, constant is used

  void foo() {
    System.out.println(used); // ok, used in inner class
  }
}
// xdoc section -- end
