/*
Indentation
basicOffset = 2
braceAdjustment = (default)0
caseIndent = 2
throwsIndent = 2
arrayInitIndent = 2
lineWrappingIndentation = (default)4
forceStrictCondition = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

public class InputIndentationWhileWrappingDifferentRelaxed {
  void test(boolean a, boolean b) {
    while (a ||
        b) {
      a = true;
    }
    while (a ||
      b) { // violation 'incorrect indentation level 6, expected level should be 8'
      a = true;
    }
    while (a ||
          b) {
      a = true;
    }
    if (a ||
        b) {
      a = true;
    }
    if (a ||
      b) { // violation 'incorrect indentation level 6, expected level should be 8'
      a = true;
    }
    if (a ||
          b) {
      a = true;
    }
  }
}
