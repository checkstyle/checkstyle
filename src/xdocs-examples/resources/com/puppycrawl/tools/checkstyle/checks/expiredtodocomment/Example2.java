/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExpiredTodoComment">
      <property name="pattern" value=
"(?i)(TODO|FIXME)\s*(?&lt;date&gt;\d{4}\.\d{2}\.\d{2})\s*(?&lt;comment&gt;.*)"/>
      <property name="format" value="yyyy.dd.MM"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.expiredtodocomment;

// xdoc section -- start
public class Example2 {
  int i;
  int x;
  public void test() {
    i++;   // TODO 2000.01.01 do differently in future    // violation
    i++;   // todo 2000.01.01 do differently in future    // violation
    i=i/x; // FIXME 2000.01.01 handle x = 0 case          // violation
    i=i/x; // FIX 2000-01-01 handle x = 0 case
  }
}
// xdoc section -- end
