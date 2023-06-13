/*
AbbreviationAsWordInName
allowedAbbreviationLength = 0
allowedAbbreviations = XML,URL,O
ignoreStatic = false
tokens = VARIABLE_DEF,CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class Example3 {
  // xdoc section -- start
  int firstNum;
  int secondNUM; // violation 'no more than '1' consecutive capital letters'
  static int thirdNum;
  static int fourthNUm; // violation 'no more than '1' consecutive capital letters'
  String firstXML;
  String firstURL;
  final int TOTAL = 5;
  static final int LIMIT = 10;
  void newOAuth2Client() {}
  void OAuth2() {}
  void OAUth2() {}
  // xdoc section -- end
}
