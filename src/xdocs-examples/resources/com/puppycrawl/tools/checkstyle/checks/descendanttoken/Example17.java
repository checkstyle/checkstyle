/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="METHOD_DEF"/>
      <property name="limitedTokens" value="LITERAL_RETURN"/>
      <property name="minimumNumber" value="1"/>
      <property name="minimumMessage"
        value="Method must contain at least one return statement."/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example17 {
  void testMethod() {
    // violation above, 'Method must contain at least one return statement.'
    int x = 1;
    x++;
  }

  int testMethodWithReturn() {
    return 1;
  }
}
// xdoc section -- end
