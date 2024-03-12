/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbbreviationAsWordInName">
      <property name="allowedAbbreviationLength" value="0"/>
      <property name="ignoreFinal" value="false"/>
      <property name="ignoreStatic" value="true"/>
      <property name="ignoreStaticFinal" value="false"/>
      <property name="tokens" value="VARIABLE_DEF"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

// xdoc section -- start
class Example6 {
  int counterXYZ = 1; // violation 'no more than '1' consecutive capital letters'
  // violation below 'no more than '1' consecutive capital letters'
  final int customerID = 2;
  static int nextID = 3; // OK, ignored
  // violation below 'no more than '1' consecutive capital letters'
  static final int MAX_ALLOWED = 4;
}
// xdoc section -- end
