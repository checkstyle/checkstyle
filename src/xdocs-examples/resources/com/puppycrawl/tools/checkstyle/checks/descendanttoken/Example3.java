/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="LITERAL_SWITCH"/>
      <property name="limitedTokens" value="LITERAL_SWITCH"/>
      <property name="maximumNumber" value="0"/>
      <property name="minimumDepth" value="1"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example3 {
  void testMethod1() {
    int x = 1;
    switch (x) {
      case 1:
        System.out.println("hi");
        break;
      default:
        System.out.println("Default");
        break;
    }

    int y = 1;
    switch (y) {
      case 1:
        System.out.println("hi");
        break;
    }
  }

  void testMethod2() {
    int x = 1;
    switch (x) {
      case 1:
        // Some code
        break;
      default:
        // Some code
        break;
    }

    switch (x) {
      case 1:
        // Some code
        break;
      case 2:
        // Some code
        break;
      default:
        // Some code
        break;
    }
  }

  void testMethod3() {
    int x = 1;
    int y = 2;
    switch (x) {
      case 1:
        System.out.println("xyz");
        break;
    }
    switch (y) { // violation, 'Count of 1 for 'LITERAL_SWITCH' descendant'
      case 1:
        switch (y) {
          case 2:
            System.out.println("xyz");
            break;
        }
        break;
    }
  }
}
// xdoc section -- end
