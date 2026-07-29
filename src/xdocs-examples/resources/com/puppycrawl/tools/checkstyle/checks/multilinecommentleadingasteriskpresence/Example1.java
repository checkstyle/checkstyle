/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MultilineCommentLeadingAsteriskPresence"/>
  </module>
</module>
*/

// violation 8 lines above """
// Multiline comment line(s) 2, 3, 4, 5, 6 should start with leading asterisk"""

package com.puppycrawl.tools.checkstyle.checks.multilinecommentleadingasteriskpresence;

// xdoc section - start
public class Example1 {

  /*
   * Line has leading asterisk.
   */
  void foo() {}

  // violation 2 lines below """Multiline comment line(s) 25
  // should start with leading asterisk"""
  /* Line with leading asterisk
     Line with leading asterisk
  */
  void bar() {}

  /* Single line comment */
  void method() {}

}
// xdoc section - end
