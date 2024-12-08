/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="CLASS_DEF,INTERFACE_DEF"/>
      <property name="limitedTokens" value="VARIABLE_DEF"/>
      <property name="maximumDepth" value="2"/>
      <property name="maximumNumber" value="1"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example13 {
  private int field1;

  // Some code
}
// violation below, 'Count of 2 for 'CLASS_DEF' descendant'
class ExampleTest {
  private int field1;
  private int field2;

  // Some code
}
interface Test1 {
  int FIELD_1 = 1;
}
// violation below,'Count of 2 for 'INTERFACE_DEF' descendant'
interface Test2 {
  int FIELD_1 = 1;
  int FIELD_2 = 2;
}
// xdoc section -- end

