/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CommentsIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// xdoc section -- start
public class Example3 {
  /////////////////////////////// it is OK

  public void foo1() {
    int a = 0;
  }
  // violation below, 'Comment has incorrect indentation level 4, expected is 2'
    ///////////////////////////// It is not OK

  public void foo2() {}
}
// xdoc section -- end
