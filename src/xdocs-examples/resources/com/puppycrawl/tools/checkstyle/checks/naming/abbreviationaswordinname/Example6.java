/*
AbbreviationAsWordInName
allowedAbbreviationLength = 0
allowedAbbreviations = (default)
ignoreFinal = false
ignoreStatic = true
ignoreStaticFinal = false
ignoreOverriddenMethods = (default)true
tokens = VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class Example6 {
  int counterXYZ = 1; // violation 'no more than '1' consecutive capital letters'
  final int customerID = 2; // violation 'no more than '1' consecutive capital letters'
  static int nextID = 3;
  static final int MAX_ALLOWED = 4; // violation 'no more than '1' consecutive capital letters'
}
