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

// xdoc section -- start
class Example2 {
  double d = 3.14;

  int a = ( int ) d;

  int b = (int ) d; // violation 'not followed by whitespace'

  int c = ( int) d; // violation 'not preceded with whitespace'

  int e = (int) d;  // 2 violations

  double d2 = 9.8;

  int f = (int) d2; // 2 violations

  int g = ( int ) d2;
}
// xdoc section -- end
