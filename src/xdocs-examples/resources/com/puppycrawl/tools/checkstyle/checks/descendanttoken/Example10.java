/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="LITERAL_THROWS"/>
      <property name="limitedTokens" value="IDENT"/>
      <property name="maximumNumber" value="1"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example10 {
  void testMethod1() throws ArithmeticException {
      // ...
  }
  // violation below, 'Count of 2 for 'LITERAL_THROWS' descendant'
  void testMethod2() throws ArithmeticException, ArithmeticException {
      // ...
  }
}

// xdoc section -- end
