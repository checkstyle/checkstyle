/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurly">
      <property name="option" value="alone"/>
      <property name="tokens" value="LITERAL_SWITCH"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

// xdoc section -- start
class Example3 {

  public void method0() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 1;
        break;
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
        x = 0; } // violation, ''}' at column 16 should be alone on a line.'
  }

}
// xdoc section -- end
