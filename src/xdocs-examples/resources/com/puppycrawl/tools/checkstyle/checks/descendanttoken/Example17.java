/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="LITERAL_SWITCH"/>
      <property name="maximumDepth" value="2"/>
      <property name="limitedTokens" value="LITERAL_DEFAULT"/>
      <property name="minimumNumber" value="1"/>
      <property name="minimumMessage"
        value="Switch must contain at least one default branch."/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example17 {
  void testMethod() {
    int x = 1;
    switch (x) { // violation, 'Switch must contain at least one default branch.'
      case 1:
        System.out.println("hi");
        break;
    }

    switch (x) {
      case 1:
        System.out.println("hi");
        break;
      default:
        System.out.println("Default");
        break;
    }
  }
}
// xdoc section -- end
