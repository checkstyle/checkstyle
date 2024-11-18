/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CommentsIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// xdoc section -- start
public class Example2 {
  public void foo1() {
    foo2();
    // it is OK
  }

  public void foo2() {
    foo3();
    //violation below, 'Comment has incorrect indentation level 8, expected is 4'
        // It is not OK
  }

  public void foo3(){}

}
// xdoc section -- end
