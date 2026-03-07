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
  float f1 = 3.14f;

  int n = ( int ) f1;

  double d = 1.234567;

  float f2 = (float ) d; // violation 'not followed by whitespace'

  float f3 = (float) d; // 2 violations

  float f4 = ( float) d; // violation 'not preceded with whitespace'
}
// xdoc section -- end
