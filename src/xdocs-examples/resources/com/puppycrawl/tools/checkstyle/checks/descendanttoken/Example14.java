/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="EQUAL,NOT_EQUAL"/>
      <property name="limitedTokens" value="LITERAL_THIS,LITERAL_NULL"/>
      <property name="maximumNumber" value="1"/>
      <property name="maximumDepth" value="1"/>
      <property name="sumTokenCounts" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example14 {
  void testMethod1() {
    // violation below, 'Total count of 2 exceeds maximum count 1'
    if (this == null) {
      System.out.println("xyz");
    }
    if (this != null) {
      // violation above, 'Total count of 2 exceeds maximum count 1'
      System.out.println("xyz");
    }

    Object obj = new Object();
    if (obj == null) {
      System.out.println("xyz");
    }
    if (obj != null) {
      System.out.println("xyz");
    }
  }
}
// xdoc section -- end

