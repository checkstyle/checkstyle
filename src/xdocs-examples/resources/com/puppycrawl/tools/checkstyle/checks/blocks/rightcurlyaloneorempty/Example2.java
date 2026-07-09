/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyAloneOrEmpty">
      <property name="tokens" value="CLASS_DEF, METHOD_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

// xdoc section -- start
class Example2 {
  void method() {
    boolean flag = true;
    if (flag) {
      int x = 20;
    } else {
      int x = 10;
    }
  }

  void method2() {
    try {
    }
    catch (Exception e) {}

    try {
    } catch (Exception e) {
    }
    finally {}

    try {
    } catch (Exception e) {
      int x = 10; }
  } // comment
  // violation above ''}' at column 3 should be alone on a line'
  class internal {
  } class internal2 {}
  // violation above ''}' at column 3 should be alone on a line'
}
// xdoc section -- end
