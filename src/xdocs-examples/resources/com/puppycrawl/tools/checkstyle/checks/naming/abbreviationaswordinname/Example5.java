/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbbreviationAsWordInName">
      <property name="allowedAbbreviationLength" value="0"/>
      <property name="ignoreFinal" value="false"/>
      <property name="ignoreStatic" value="false"/>
      <property name="ignoreStaticFinal" value="true"/>
      <property name="tokens" value="VARIABLE_DEF"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import java.util.HashSet;
import java.util.Set;

// xdoc section -- start
class Example5 extends SuperClass {
  int CURRENT_COUNTER; // violation 'no more than '1' consecutive capital letters'
  // violation below 'no more than '1' consecutive capital letters'
  static int GLOBAL_COUNTER;
  // violation below 'no more than '1' consecutive capital letters'
  final Set<String> stringsFOUND = new HashSet<>();
  int firstNum;
  int secondNUM; // violation 'no more than '1' consecutive capital letters'
  static int thirdNum;
  static int fourthNUm; // violation 'no more than '1' consecutive capital letters'
  String firstXML; // violation 'no more than '1' consecutive capital letters'
  String firstURL; // violation 'no more than '1' consecutive capital letters'
  // violation below 'no more than '1' consecutive capital letters'
  final int TOTAL = 5;
  static final int LIMIT = 10; // ok, ignored
  int counterXYZ = 1; // violation 'no more than '1' consecutive capital letters'
  // violation below 'no more than '1' consecutive capital letters'
  final int customerID = 2;
  static int nextID = 3; // violation 'no more than '1' consecutive capital letters'
  static final int MAX_ALLOWED = 4; // ok, ignored

  void newOAuth2Client() {}
  void OAuth2() {}
  void OAUth2() {}

  @Override
  public void printCOUNTER() {
    System.out.println(CURRENT_COUNTER);
  }
  void incrementCOUNTER() {
    CURRENT_COUNTER++;
  }
  static void incrementGLOBAL() {
    GLOBAL_COUNTER++;
  }
}
// xdoc section -- end
