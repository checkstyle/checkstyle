/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="EQUAL,NOT_EQUAL"/>
      <property name="limitedTokens" value="STRING_LITERAL"/>
      <property name="maximumNumber" value="0"/>
      <property name="maximumDepth" value="1"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example15 {
  void testMethod1() {
    String str = "abc";
    if (str.equals("abc")) {
      System.out.println("equal.");
    }
    if (str == "abc") { // violation, 'Count of 1 for 'EQUAL' descendant'
      System.out.println("equal.");
    }
  }
}
// xdoc section -- end
