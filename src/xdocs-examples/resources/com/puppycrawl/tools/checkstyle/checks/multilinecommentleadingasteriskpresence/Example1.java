/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MultilineCommentLeadingAsteriskPresence"/>
  </module>
</module>
*/

// violation 7 lines above 'Multiline comment should start with leading asterisk'
// violation 7 lines above 'Multiline comment should start with leading asterisk'
// violation 7 lines above 'Multiline comment should start with leading asterisk'
// violation 7 lines above 'Multiline comment should start with leading asterisk'
// violation 7 lines above 'Multiline comment should start with leading asterisk'

package com.puppycrawl.tools.checkstyle.checks.multilinecommentleadingasteriskpresence;

// xdoc section -- start
public class Example1 {

  /*
   * Line has leading asterisk.
   */
  void foo() {}

  // violation 2 lines below 'Multiline comment should start with leading asterisk'
  /* Line with leading asterisk
     Line with leading asterisk
  */
  void bar() {}

  /* Single line comment */
  void method() {}

}
// xdoc section -- end
