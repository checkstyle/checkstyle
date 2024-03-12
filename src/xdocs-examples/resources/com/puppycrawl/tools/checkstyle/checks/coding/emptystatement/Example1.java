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
    if(i > 3); // violation
    i++;
    for (i = 0; i < 5; i++); // violation
    i++;
    while (i > 10)
      i++;
  }
}
// xdoc section -- end
