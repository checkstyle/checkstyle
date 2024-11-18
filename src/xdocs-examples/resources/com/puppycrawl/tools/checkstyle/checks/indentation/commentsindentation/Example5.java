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
      case "3": // it is OK
        if (true) {}
        // violation below, 'Comment has incorrect indentation level 12'
            //It is not OK
        break;
      // fall through (it is OK)
      case "4":
        int c;
        break;
      case "5":
        // it is OK
      case "6":
     // It is not OK
      case "7":
       // violation 2 lines above 'Comment has incorrect indentation level 5'
      default:
    }
  }
}
// xdoc section -- end
