/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyAloneOrEmpty"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

// xdoc section -- start
class Example1 {
  void method() {
    boolean flag = true;
    if (flag) {
      int x = 20;
    } else { // violation ''}' at column 5 should be alone on a line'
      int x = 10;
    }
  }

  void method2() {
    try {
    }
    catch (Exception e) {} // violation ''}' at column 26 should be alone on a line'

    try {
    } catch (Exception e) { // violation ''}' at column 5 should be alone on a line'
    }
    finally {} // violation ''}' at column 14 should be alone on a line'

    try {
    } catch (Exception e) { // violation ''}' at column 5 should be alone on a line'
      int x = 10; } // violation ''}' at column 19 should be alone on a line'
  } // comment

  class internal {
  } class internal2 {}

}
// xdoc section -- end
