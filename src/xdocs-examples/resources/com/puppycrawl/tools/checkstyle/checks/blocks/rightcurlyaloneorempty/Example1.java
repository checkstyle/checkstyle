/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyAloneOrEmpty"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

// xdoc section -- start
public class Example1 {
  public void test2() {
    if (true) {
      int x = 20;
    } else { // violation ''}' at column 5 should be alone on a line'
      int x = 10;
    }
  }

  public void test6() {
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
  }
}
// xdoc section -- end
