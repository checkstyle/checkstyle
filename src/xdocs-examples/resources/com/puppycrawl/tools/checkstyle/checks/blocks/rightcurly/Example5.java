/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurly">
      <property name="option" value="alone_or_singleline"/>
      <property name="tokens" value="LITERAL_SWITCH"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

// xdoc section -- start
class Example5 {

  public void method0() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 1;
        break;
      default:
        x = 0;
    }
  }

  public static void method7() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 5;
    }
  }

  public void method() {
    int mode = 0;
    int x;
    switch (mode) {
      case 1:
        x = 1; } // violation, ''}' at column 16 should be alone on a line.'
  }
}
// xdoc section -- end
