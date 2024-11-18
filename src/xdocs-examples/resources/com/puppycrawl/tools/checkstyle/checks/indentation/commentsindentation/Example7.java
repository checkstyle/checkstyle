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
  void testMethod() {
    String s1 = "Clean code!";
    s1.toString().toString().toString();
    // single-line
    // block
    // comment (it is OK)
    int a = 5;

    String s2 = "Code complete!";
    s1.toString().toString().toString();
              // It is not okay1
         //It is not okay2
       //It is not okay3
    int b = 18;
    // violation 4 lines above 'Comment has incorrect indentation level 14'
    // violation 4 lines above 'Comment has incorrect indentation level 9'
    // violation 4 lines above 'Comment has incorrect indentation level 7'
  }
}
// xdoc section -- end
