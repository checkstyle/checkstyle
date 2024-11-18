/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CommentsIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// xdoc section -- start
public class Example6 {
  void testMethod() {
    // violation 2 lines below 'Comment has incorrect indentation level 4'
    String breaks = "J"
    // It is not OK
        + "A"
        // it is OK
        + "V"
        + "A"
    // it is OK
    ;

  }
}
// xdoc section -- end
