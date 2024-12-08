/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="METHOD_DEF"/>
      <property name="limitedTokens" value="EXPR"/>
      <property name="maximumNumber" value="2"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example11 {
  void testMethod1() {
    int x = 1;
    int z = x + 2;
  }
  // violation below, 'Count of 3 for 'METHOD_DEF' descendant'
  void testMethod2() {
    int x = 1;
    int y = 2;
    int z = x + y;
  }
}
// xdoc section -- end
