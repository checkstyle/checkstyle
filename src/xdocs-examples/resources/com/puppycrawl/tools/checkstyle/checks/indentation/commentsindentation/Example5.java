/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CommentsIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// xdoc section -- start
public class Example5 {
  void testMethod(String a) {
    switch(a) {
      case "1":
        int j = 7;
        // it is OK
        break;
      case "3": // violation 2 lines below 'Comment has incorrect indentation level'
        if (true) {}
            //It is not okay
        break;
      // fall through (it is OK)
      case "5":
        // it is OK
      case "6":
     // It is not okay
      case "7":
       // violation 2 lines above 'Comment has incorrect indentation level 5'
      default:
    }
  }
}
// xdoc section -- end
