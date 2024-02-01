/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TodoComment">
      <property name="format" value="(TODO)|(FIXME)"/>
    </module>
  </module>
</module>
*/
// violation 9 lines above
package com.puppycrawl.tools.checkstyle.checks.todocomment;

// xdoc section -- start
public class Example2 {
    int i;
    int x;
    public void test() {
        i++;   // TODO: do differently in future   // violation, 'Comment matches .*'
        i++;   // todo: do differently in future
        i=i/x; // FIXME: handle x = 0 case         // violation, 'Comment matches .*'
        i=i/x; // FIX :  handle x = 0 case
    }
}
// xdoc section -- end
