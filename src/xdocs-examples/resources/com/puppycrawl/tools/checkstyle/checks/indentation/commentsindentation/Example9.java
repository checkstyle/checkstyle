/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CommentsIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// xdoc section -- start
public class Example9 {
  public void foo42() {
    int a = 5;
    if (a == 5) {
      int b;
      // it is OK
    } else if (a ==6) {}
  }

  public void foo43() {
    try {
      int a;
    // violation below, 'Comment has incorrect indentation level 5, expected is 6'
     // It is not OK
    } catch (Exception e) {  }
  }
}
// xdoc section -- end
