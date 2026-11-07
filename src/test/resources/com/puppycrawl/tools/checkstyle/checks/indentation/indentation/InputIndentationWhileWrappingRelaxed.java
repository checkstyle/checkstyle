/*
Indentation
basicOffset = 2
braceAdjustment = (default)0
caseIndent = 2
throwsIndent = 2
arrayInitIndent = 2
lineWrappingIndentation = 2
forceStrictCondition = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

public class InputIndentationWhileWrappingRelaxed {
  void test(boolean a, boolean b, boolean c) {
    while (a ||
                    b ||
                 c) {
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
