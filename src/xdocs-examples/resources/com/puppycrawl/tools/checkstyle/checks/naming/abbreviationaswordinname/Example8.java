/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbbreviationAsWordInName">
      <property name="allowedAbbreviationLength" value="1"/>
      <property name="allowedAbbreviations" value="COUNTER,GLOBAL"/>
      <property name="ignoreOverriddenMethods" value="false"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import java.util.HashSet;
import java.util.Set;

// xdoc section -- start
class Example8 extends SuperClass { // ok, camel case
  int CURRENT_COUNTER;  // violation 'no more than '2' consecutive capital letters'

  static int GLOBAL_COUNTER;        // ok, GLOBAL and COUNTER are allowed

  final Set<String> stringsFOUND = new HashSet<>(); // ok, final is ignored
  int firstNum;
  int secondNUM;         // violation 'no more than '2' consecutive capital letters'
  static int thirdNum;
  static int fourthNUm;
  String firstXML;       // violation 'no more than '2' consecutive capital letters'
  String firstURL;       // violation 'no more than '2' consecutive capital letters'
  final int TOTAL = 5;               // ok, final is ignored
  static final int LIMIT = 10;       // ok, static final is ignored
  int nextXYZ = 1;        // violation 'no more than '2' consecutive capital letters'
  final int countID = 2;             // ok, final is ignored

  static int nextID = 3;
  static final int MAX_ALLOWED = 4;  // ok, static final is ignored

  void newOAuth2Client() {}
  void OAuth2() {}
  void OAUth2() {}        // violation 'no more than '2' consecutive capital letters'

  @Override
  public void printCOUNTER() {}     // ok, COUNTER is allowed

  void incrementCOUNTER() {}        // ok, COUNTER is allowed

  static void incrementGLOBAL() {}  // ok, GLOBAL is allowed
}
// xdoc section -- end
