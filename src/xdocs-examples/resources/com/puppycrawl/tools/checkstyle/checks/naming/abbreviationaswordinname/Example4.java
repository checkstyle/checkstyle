/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbbreviationAsWordInName">
      <property name="allowedAbbreviationLength" value="1"/>
      <property name="allowedAbbreviations" value="CSV"/>
      <property name="ignoreStatic" value="true"/>
      <property name="tokens" value="VARIABLE_DEF"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

// xdoc section -- start
class Example4 { // ok, ignore checking the class name
  int firstNum; // ok, abbreviation "N" is of allowed length 1
  int secondNUm;
  int secondMYNum; // violation 'no more than '2' consecutive capital letters'
  int thirdNUM; // violation 'no more than '2' consecutive capital letters'
  static int fourthNUM; // ok, variables with static modifier would be ignored
  String firstCSV; // ok, CSV abbreviation is allowed
  String firstXML; // violation 'no more than '2' consecutive capital letters'
  final int TOTAL = 5; // ok, final is ignored
  static final int LIMIT = 10; // ok, static final is ignored
}
// xdoc section -- end
