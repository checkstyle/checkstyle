/*
AbbreviationAsWordInName
allowedAbbreviationLength = 1
allowedAbbreviations = CSV
ignoreFinal = (default)true
ignoreStatic = true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class Example4 {
  int firstNum;
  int secondNUm;
  int secondMYNum; // violation 'no more than '2' consecutive capital letters'
  int thirdNUM; // violation 'no more than '2' consecutive capital letters'
  static int fourthNUM;
  String firstCSV;
  String firstXML; // violation 'no more than '2' consecutive capital letters'
  final int TOTAL = 5;
  static final int LIMIT = 10;
}
