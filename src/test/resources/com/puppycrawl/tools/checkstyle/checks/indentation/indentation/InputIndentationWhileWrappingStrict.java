/*
Indentation
basicOffset = 2
braceAdjustment = (default)0
caseIndent = 2
throwsIndent = 2
arrayInitIndent = 2
lineWrappingIndentation = 2
forceStrictCondition = true

*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

public class InputIndentationWhileWrappingStrict {
  void test(boolean a, boolean b, boolean c) {
    while (a ||
                    b || // violation 'incorrect indentation level 20, expected level should be 6'
                 c) { // violation 'incorrect indentation level 17, expected level should be 6'
      a = true;
    }
    while (a ||
      b ||
      c) {
      a = true;
    }
    while (
      a || b || c
    ) {
      a = true;
    }
    while (a ||
      (b && c))
      a = true;
    do {
      a = true;
    } while (a ||
      b || c);
  }
}
