/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="EMPTY_STAT"/>
      <property name="limitedTokens" value="EMPTY_STAT"/>
      <property name="maximumNumber" value="0"/>
      <property name="maximumDepth" value="0"/>
      <property name="maximumMessage"
        value="Empty statement is not allowed."/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example12 {
  void testMethod1 () {
    System.out.println("Hello");
  }
  void testMethod2() {
      ; // violation, 'Empty statement is not allowed'
  }
}
// xdoc section -- end
