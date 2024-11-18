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
  // violation 4 lines below 'Comment has incorrect indentation level 1'
  public void foo2() {
 // comment
 // block
 // It is not okay
  }
}
// xdoc section -- end
