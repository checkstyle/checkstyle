/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbbreviationAsWordInName">
      <property name="allowedAbbreviationLength" value="0"/>
      <property name="allowedAbbreviations" value="XML,URL,O"/>
      <property name="ignoreStatic" value="false"/>
      <property name="tokens" value="VARIABLE_DEF,CLASS_DEF"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

// xdoc section -- start
class Example3 {
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
}
// xdoc section -- end
