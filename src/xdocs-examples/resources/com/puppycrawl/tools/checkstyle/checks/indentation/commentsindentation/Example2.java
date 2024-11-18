/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CommentsIndentation">
      <property name="tokens" value="SINGLE_LINE_COMMENT"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// xdoc section -- start
public class Example2 {
  void testMethod() {
    /*
     * it is Ok
     */
    boolean bool = true;

      /*
       * It is not okay
       */
    double d = 3.14;
  }

  public void foo1() {
    foo2();
    // it is OK
  }

  public void foo2() {
    int i;
        // It is not okay
  }
  // violation 2 lines above  'Comment has incorrect indentation level 8'
}
// xdoc section -- end
