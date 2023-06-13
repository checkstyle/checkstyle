/*
AbbreviationAsWordInName
allowedAbbreviationLength = 0
allowedAbbreviations = (default)
ignoreFinal = false
ignoreStatic = false
ignoreStaticFinal = true
ignoreOverriddenMethods = (default)true
tokens = VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class Example5 {
  int counterXYZ = 1; // violation 'no more than '1' consecutive capital letters'
  final int customerID = 2; // violation 'no more than '1' consecutive capital letters'
  static int nextID = 3; // violation 'no more than '1' consecutive capital letters'
  static final int MAX_ALLOWED = 4;
}
