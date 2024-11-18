/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CommentsIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// xdoc section -- start
public class Example8 {
  public void foo42() {
    int a = 5;
    if (a == 5) {
      int b;
      // it is OK
    } else if (a ==6) {}
  }

  public void foo43() {
    // violation 3 lines below 'Comment has incorrect indentation level 5'
    try {
      int a;
     // It is not OK
    } catch (Exception e) {  }
  }
}
// xdoc section -- end
