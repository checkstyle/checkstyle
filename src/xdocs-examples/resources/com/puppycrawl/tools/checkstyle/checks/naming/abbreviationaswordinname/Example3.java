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
  static int thirdNum; // ok, the static modifier would be checked
  static int fourthNUm; // violation 'no more than '1' consecutive capital letters'
  String firstXML; // ok, XML abbreviation is allowed
  String firstURL; // ok, URL abbreviation is allowed
  final int TOTAL = 5; // ok, final is ignored
  static final int LIMIT = 10; // ok, static final is ignored
  void newOAuth2Client() {} // ok, O abbreviation is allowed
  void OAuth2() {} // ok, O abbreviation is allowed
  void OAUth2() {}
}
// xdoc section -- end
