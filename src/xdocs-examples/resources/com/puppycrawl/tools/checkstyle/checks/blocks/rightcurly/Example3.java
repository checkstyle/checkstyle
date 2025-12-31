/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurly">
      <property name="option" value="alone"/>
      <property name="tokens" value="LITERAL_SWITCH, LITERAL_CASE"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

// xdoc section -- start
public class Example3 {

  public void method0() {
    int mode = 0;
    int x;
    switch (mode) {
      case 1:
        int y = 1;
        break;
      case 2: {x = 1;}   // violation '}' at column 22 should be alone on a line'
      case 3: int z = 0; {break;} // ok, the braces is not a first child of case
      default:
        x = 0;
    } // ok, RightCurly is alone
  }

  public void method01() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 1;
        break;
      default:
        x = 0; }
    // violation above, 'should be alone on a line.'
  }
}
// xdoc section -- end
