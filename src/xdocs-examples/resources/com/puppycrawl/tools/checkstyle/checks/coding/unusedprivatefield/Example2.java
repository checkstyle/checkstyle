/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedPrivateField">
    <property name="ignoreAnnotationCanonicalNames" value="Getter,Serial"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

// xdoc section -- start

import java.io.Serial;

public class Example2 {

  @interface Getter {}

  private int unused; // violation 'Unused private field'

  private int used;

  @Serial
  private static final long serialVersionUID = 1L;

  @Getter
  private int getter; // ok, as is supressed by property

  private int getUsed(){
    return used;
  }

  private static final int CONSTANT = 10; // violation 'Unused private field'

  void foo() {
    System.out.println(used); // ok, used in inner class
  }
}
// xdoc section -- end
