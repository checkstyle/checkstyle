/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyStatement"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

// xdoc section -- start
public class Example1 {
  public void foo() {
    int i = 5;
    if(i > 3); // violation 'Empty statement'
    i++;
    for (i = 0; i < 5; i++); // violation 'Empty statement'
    i++;
    while (i > 10)
      i++;
  }
}
// xdoc section -- end
