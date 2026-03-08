/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MultilineCommentLeadingAsteriskPresence"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.multilinecommentleadingasteriskpresence;

// xdoc section -- start
class Example1 {
  // OK: Single-line block comment
  /* This is fine */

  // OK: Multi-line with asterisks
  /*
   * This is correct.
   * Each line has asterisk.
   */
  void method1() { }

  // violation below 'Line in block comment should start with '*''
  /*
   First line missing asterisk.
   * Second line OK.
   */
  void method2() { }

  // violation 2 lines below 'Line in block comment should start with '*''
  /*
   * First line OK.
   Second line missing asterisk.
   */
  void method3() { }
}
// xdoc section -- end

