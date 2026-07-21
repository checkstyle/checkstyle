/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbbreviationAsWordInName">
      <property name="ignoreFinal" value="false"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import java.util.HashSet;
import java.util.Set;

// xdoc section -- start
class Example4 extends SuperClass { // ok, camel case
  int CURRENT_COUNTER;  // violation 'no more than '4' consecutive capital letters'

  static int GLOBAL_COUNTER;        // ok, static is ignored
  // violation below 'no more than '4' consecutive capital letters'
  final Set<String> stringsFOUND = new HashSet<>();
  int firstNum;
  int secondNUM;
  static int thirdNum;
  static int fourthNUm;
  String firstXML;
  String firstURL;
  final int TOTAL = 5;  // violation 'no more than '4' consecutive capital letters'
  static final int LIMIT = 10;      // ok, static final is ignored
  int nextXYZ = 1;
  final int countID = 2;            // ok, final is checked but run is short

  static int nextID = 3;
  static final int MAX_ALLOWED = 4; // ok, static final is ignored

  void newOAuth2Client() {}
  void OAuth2() {}
  void OAUth2() {}

  @Override
  public void printCOUNTER() {}     // ok, overridden method is ignored
  // violation below 'no more than '4' consecutive capital letters'
  void incrementCOUNTER() {}
  // violation below 'no more than '4' consecutive capital letters'
  static void incrementGLOBAL() {}
}
// xdoc section -- end
