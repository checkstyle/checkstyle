/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyStatement">
      <property name="allowEmptyLoopBody" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

// xdoc section - start
public class Example2 {
  ; // violation 'Empty statement'
  public void foo() {
    int i = 5;
    if(i > 3); // violation 'Empty statement'
    i++;
    for (i = 0; i < 5; i++); // ok, empty loop body is allowed
    i++;
    while (i > 10)
      i++;
  }
  ; // violation 'Empty statement'
}
// xdoc section - end
