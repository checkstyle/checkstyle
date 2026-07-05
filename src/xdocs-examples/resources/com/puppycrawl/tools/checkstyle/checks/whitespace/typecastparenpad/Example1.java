/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypecastParenPad"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typecastparenpad;

// xdoc section -- start
class Example1 {
  double d = 3.14;

  int a = ( int ) d;  // 2 violations

  int b = (int ) d;   // violation 'preceded with whitespace'

  int c = ( int) d;   // violation 'followed by whitespace'

  int e = (int) d;

  double d2 = 9.8;

  int f = (int) d2;

  int g = ( int ) d2; // 2 violations
}
// xdoc section -- end
