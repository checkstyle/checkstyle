/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TodoComment">
      <property name="format" value="(?i)(TODO)|(FIXME)"/>
    </module>
  </module>
</module>
*/
// violation 9 lines above 'matches to-do format'
package com.puppycrawl.tools.checkstyle.checks.todocomment;

// xdoc section -- start
public class Example2 {
  int i;
  int x;
  public void test() {
    // violation below 'matches to-do format'
    i++;   // TODO: do differently in future
    // violation below 'matches to-do format'
    i++;   // todo: do differently in future
    // violation below 'matches to-do format'
    i=i/x; // FIXME: handle x = 0 case
    i=i/x; // FIX :  handle x = 0 case
  }
}
// xdoc section -- end
