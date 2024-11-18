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
    ///////////////////////////// It is not OK

  // violation 2 lines above 'Comment has incorrect indentation level 4'
  public void foo2() {}
}
// xdoc section -- end
