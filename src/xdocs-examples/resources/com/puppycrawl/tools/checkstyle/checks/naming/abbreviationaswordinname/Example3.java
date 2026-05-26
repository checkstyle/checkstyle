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

import java.util.HashSet;
import java.util.Set;

// xdoc section -- start
class Example3 extends SuperClass {
  int CURRENT_COUNTER;  // violation 'no more than '1' consecutive capital letters'
  // violation below 'no more than '1' consecutive capital letters'
  static int GLOBAL_COUNTER;

  final Set<String> stringsFOUND = new HashSet<>(); // ok, final is ignored
  int firstNum;
  int secondNUM;        // violation 'no more than '1' consecutive capital letters'
  static int thirdNum;  // ok, the static modifier would be checked
  static int fourthNUm; // violation 'no more than '1' consecutive capital letters'
  String firstXML;      // ok, XML abbreviation is allowed
  String firstURL;      // ok, URL abbreviation is allowed
  final int TOTAL = 5;              // ok, final is ignored
  static final int LIMIT = 10;      // ok, static final is ignored
  int nextXYZ = 1;      // violation 'no more than '1' consecutive capital letters'
  final int countID = 2;            // ok, final is ignored
  // violation below 'no more than '1' consecutive capital letters'
  static int nextID = 3;
  static final int MAX_ALLOWED = 4; // ok, static final is ignored

  void newOAuth2Client() {} // ok, O abbreviation is allowed
  void OAuth2() {}          // ok, O abbreviation is allowed
  void OAUth2() {}

  @Override
  public void printCOUNTER() {}

  void incrementCOUNTER() {}

  static void incrementGLOBAL() {}
}
// xdoc section -- end
