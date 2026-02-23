/*
RightCurly
option = SAME
tokens = LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

/** some javadoc. */
public class InputRightCurlyTestElseFalseNegative {

    /** some javadoc. */
  void foo() {
  int a = 18;

  if (a == 18) {
    } else {} // violation '}' at column x

  if (a == 18) {
    } else { } // violation '}' at column x

    if (a == 18) {} else {} // violation '}' at column x

    if (a == 18) {
    } // ok - no else

    if (a == 19) {}
  }

  void bar() {} // ok
}