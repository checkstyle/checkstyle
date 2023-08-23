/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbbreviationAsWordInName">
      <property name="allowedAbbreviations" value="ALLOWED"/>
      <property name="ignoreStaticFinal" value="false"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

// xdoc section -- start
class Example7 {
  int counterXYZ = 1;
  final int customerID = 2;
  static int nextID = 3;
  static final int MAX_ALLOWED = 4; // OK, abbreviation is allowed
}
// xdoc section -- end
