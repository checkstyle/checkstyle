/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbbreviationAsWordInName">
      <property name="tokens" value="METHOD_DEF"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import java.util.HashSet;
import java.util.Set;

// xdoc section -- start
class Example7 extends SuperClass { // ok, camel case
  int CURRENT_COUNTER;  // ok, only METHOD_DEF is checked

  static int GLOBAL_COUNTER;        // ok, only METHOD_DEF is checked

  final Set<String> stringsFOUND = new HashSet<>(); // ok, only METHOD_DEF is checked
  int firstNum;
  int secondNUM;
  static int thirdNum;
  static int fourthNUm;
  String firstXML;
  String firstURL;
  final int TOTAL = 5;              // ok, only METHOD_DEF is checked
  static final int LIMIT = 10;      // ok, only METHOD_DEF is checked
  int nextXYZ = 1;
  final int countID = 2;

  static int nextID = 3;
  static final int MAX_ALLOWED = 4; // ok, only METHOD_DEF is checked

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
