/*
AbbreviationAsWordInName
allowedAbbreviationLength = 0
ignoreFinal = false
ignoreStatic = false
ignoreStaticFinal = true
tokens = VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class Example5 {
  // xdoc section -- start
  int counterXYZ = 1; // violation 'no more than '1' consecutive capital letters'
  // violation below 'no more than '1' consecutive capital letters'
  final int customerID = 2;
  static int nextID = 3; // violation 'no more than '1' consecutive capital letters'
  static final int MAX_ALLOWED = 4;
  // xdoc section -- end
}
