/*
Indentation
arrayInitIndent = (default)4
basicOffset = 2
braceAdjustment = (default)0
caseIndent = (default)4
forceStrictCondition = (default)false
lineWrappingIndentation = (default)4
tabWidth = 4
throwsIndent = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

public class InputIndentationEmptyArgumentList {
  void test() {
    toString(
    ); // violation ''method call' child has incorrect indentation level 4, expected level should be 6.'
    toString();
  }
}
