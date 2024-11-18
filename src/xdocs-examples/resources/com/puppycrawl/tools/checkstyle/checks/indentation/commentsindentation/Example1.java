/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CommentsIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// xdoc section -- start
public class Example1 {
  void testMethod() {
    /*
     * it is Ok
     */
    boolean bool = true;
      // violation below, 'Block comment has incorrect indentation level 6'
      /*
       * It is not okay
       */
    double d = 3.14;
  }
}
// xdoc section -- end
