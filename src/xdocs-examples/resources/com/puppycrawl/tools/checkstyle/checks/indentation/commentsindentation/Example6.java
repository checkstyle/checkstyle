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
    String breaks = "J"
        // violation below, 'Comment has incorrect indentation level 4'
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
