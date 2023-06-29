/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypecastParenPad">
      <property name="option" value="space"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typecastparenpad;

class Example2 {
  // xdoc section -- start
  double d1 = 3.14;

  int n = ( int ) d1;

  int m = (int ) d1; // violation 'not followed by whitespace'

  double d2 = 9.8;

  int x = (int) d2; // 2 violations

  int y = ( int) d2; // violation 'not preceded with whitespace'
  // xdoc section -- end
}
