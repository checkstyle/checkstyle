/*
TypecastParenPad


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typecastparenpad;

class Example1 {
  // xdoc section -- start
  float f1 = 3.14f;

  int n = ( int ) f1; // 2 violations

  double d = 1.234567;

  float f2 = (float ) d; // violation 'preceded with whitespace'

  float f3 = (float) d;

  float f4 = ( float) d; // violation 'followed by whitespace'
  // xdoc section -- end
}
