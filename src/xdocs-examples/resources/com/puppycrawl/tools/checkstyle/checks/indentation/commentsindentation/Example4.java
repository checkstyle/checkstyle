/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CommentsIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// xdoc section -- start
public class Example4 {
  public void foo1() {
    // comment
    // block
    // it is OK (we cannot clearly detect user intention of explanation target)
  }

  public void foo2() {
 // comment
 // block
 // violation below, 'Comment has incorrect indentation level 1, expected is 2'
 // It is not OK
  }
}
// xdoc section -- end
