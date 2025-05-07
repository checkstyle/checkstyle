/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExpiredTodoComment">
      <property name="format" value="yyyy.MM.dd" />
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.expiredtodocomment;

// xdoc section -- start
public class Example3 {
  int i;
  int x;
  public void test() {
    i++;   // TODO 01.01.2000 do differently in future
    i++;   // todo 01 01 2000 do differently in future
    i=i/x; // FIXME 01-01-2050 handle x = 0 case
    i=i/x; // FIX :  handle x = 0 case
  }
}
// xdoc section -- end
