/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyAloneOrEmpty">
      <property name="allowMultiBlock" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

// xdoc section -- start
class Example3 {
  void method() {
    boolean flag = true;
    if (flag) {
      int x = 20;
    } else {                // ok, multi-block statement
      int x = 10;
    }
  }

  void method2() {
    try {
    }
    catch (Exception e) {}
    // violation above ''}' at column 26 should be alone on a line'
    try {
    } catch (Exception e) { // ok, multi-block statement
    }
    finally {}
    // violation above ''}' at column 14 should be alone on a line'
    try {
    } catch (Exception e) { // ok, multi-block statement
      int x = 10; } // violation ''}' at column 19 should be alone on a line'
  } // comment

  class internal {
  } class internal2 {}

}
// xdoc section -- end
