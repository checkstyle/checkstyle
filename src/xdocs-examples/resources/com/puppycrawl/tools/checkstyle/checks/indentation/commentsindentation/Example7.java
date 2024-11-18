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
              // It is Not OK1
         //It is Not OK2
       //It is Not OK3
    int b = 18;
    // violation 4 lines above 'Comment has incorrect indentation level 14'
    // violation 4 lines above 'Comment has incorrect indentation level 9'
    // violation 4 lines above 'Comment has incorrect indentation level 7'
  }
}
// xdoc section -- end
