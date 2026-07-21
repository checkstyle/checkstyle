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
  // violation below ''(' is followed by whitespace.'
  int a = ( int ) d;
  // violation above '')' is preceded with whitespace.'
  int b = (int ) d;   // violation 'preceded with whitespace'

  int c = ( int) d;   // violation 'followed by whitespace'

  int e = (int) d;

  double d2 = 9.8;

  int f = (int) d2;
  // violation below ''(' is followed by whitespace.'
  int g = ( int ) d2;
  // violation above '')' is preceded with whitespace.'
}
// xdoc section -- end
