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
      case "2":
        int k = 7;
        break;
      // it is OK
      case "3":
        if (true) {}
        // violation below, 'Comment has incorrect indentation level 12'
            //It is not OK
        break;
      case "4":
        break;
      case "5":
        int b;

      // fall through (it is OK)
      case "12":
        int c;
        break;
      default:
        // it is OK
    }
  }
}
// xdoc section -- end
