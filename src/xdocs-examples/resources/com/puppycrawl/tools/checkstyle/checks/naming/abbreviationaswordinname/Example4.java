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
// xdoc section -- end
