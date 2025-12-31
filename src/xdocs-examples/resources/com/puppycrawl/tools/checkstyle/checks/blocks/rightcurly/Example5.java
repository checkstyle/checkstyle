/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurly">
      <property name="option" value="alone_or_singleline"/>
      <property name="tokens" value="LITERAL_SWITCH, LITERAL_CASE"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

// xdoc section -- start
public class Example5 {

  public void method0() {
    int mode = 0;
    int x;
    switch (mode) {
      case 1:
        int y = 1;
        break;
      case 2: {x = 1;}   // ok, RightCurly is in single line
      case 3: int z = 0; {break;} // ok, the braces is not a first child of case
      default:
        x = 0;
    }
  }

  public static void method7() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 5;
    } // ok, RightCurly is on the same line as LeftCurly
  }

  public void method() {
    int mode = 0;
    int x;
    switch (mode) {
      case 1:
        x = 1; }
    // violation above, 'should be alone on a line.'
  }
}
// xdoc section -- end
