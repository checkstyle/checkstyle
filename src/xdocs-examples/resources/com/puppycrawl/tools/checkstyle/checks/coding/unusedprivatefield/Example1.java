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
  private int unused; // violation, 'Unused private field 'unused''
  private int usedInMethod;
  private int assignedOnly; // violation, 'Unused private field 'assignedOnly''
  // violation below, 'Unused private field 'CONSTANT''
  private static final int CONSTANT = 10;
  // violation below, 'Unused private field 'staticUnused''
  private static int staticUnused;
  private long serialVersionUID = 1L; // ok, serialVersionUID is excluded

  public void foo() {
    System.out.println(usedInMethod);
    assignedOnly = 5;
  }

  class Inner1 {
    private int x; // ok, read by Inner2 via dot access
    private int y; // violation, 'Unused private field 'y''
  }

  class Inner2 {
    Inner1 obj = new Inner1();
    int read = obj.x;
  }
}
// xdoc section -- end
