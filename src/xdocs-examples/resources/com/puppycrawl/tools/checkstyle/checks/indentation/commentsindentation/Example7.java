/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CommentsIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// xdoc section -- start
public class Example7 {
  void testMethod(int a) {
    switch (a) {
      case 4:
        // it is OK
      case 5:
      // violation below, 'Comment has incorrect indentation level 5, expected is 6'
     // It is not OK
      case 6:

      default:
    }
  }
}
// xdoc section -- end
