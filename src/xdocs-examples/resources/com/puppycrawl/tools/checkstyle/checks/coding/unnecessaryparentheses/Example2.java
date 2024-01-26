/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryParentheses">
      <property name="tokens" value="BOR, BAND, BXOR" />
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

// xdoc section -- start
class Example2 {

  void method() {
    int x = 9, y = 8;
    // violation below, 'Unnecessary parentheses around expression'
    if(x>= 0 ^ (x<=8 & y<=11)
         ^ y>=8) {
      return;
    }
    if(x>= 0 ^ x<=8 & y<=11 ^ y>=8) {
      return;
    }
    // violation below, 'Unnecessary parentheses around expression'
    if(x>= 0 || (x<=8 & y<=11)
        && y>=8) {
      return;
    }
    if(x>= 0 || x<=8 & y<=11 && y>=8) {
      return;
    }
    if(x>= 0 & (x<=8 ^ y<=11) & y>=8) {
      return;
    }
  }

}
// xdoc section -- end
