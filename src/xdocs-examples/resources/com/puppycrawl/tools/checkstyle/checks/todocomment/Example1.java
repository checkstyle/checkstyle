/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TodoComment"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.todocomment;

// xdoc section -- start
public class Example1 {
  int i;
  int x;
  public void test() {
    i++;   // TODO: do differently in future    // violation
    i++;   // todo: do differently in future
    i=i/x; // FIXME: handle x = 0 case
    i=i/x; // FIX :  handle x = 0 case
  }
}
// xdoc section -- end
