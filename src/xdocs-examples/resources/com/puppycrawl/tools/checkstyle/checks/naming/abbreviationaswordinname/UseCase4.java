/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbbreviationAsWordInName">
      <property name="allowedAbbreviationLength" value="0"/>
      <property name="allowedAbbreviations" value="SLF4,SLF4J,UTF8"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

// xdoc section - start
class UseCase4 {
  void getSLF4JLogger() { }
  void getSLF4Adapter() { }
  void getUTF8() { }
  void getSLF4JXMLParser() { } // violation 'Abbreviation in name'
  void getSLF5JLogger() { } // violation 'Abbreviation in name'
}
// xdoc section - end
