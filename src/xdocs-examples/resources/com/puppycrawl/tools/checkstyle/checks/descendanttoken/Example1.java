/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="LITERAL_SWITCH"/>
      <property name="maximumDepth" value="2"/>
      <property name="limitedTokens" value="LITERAL_DEFAULT"/>
      <property name="minimumNumber" value="1"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example1 {
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
    switch (y) { // violation, 'Count of 0 for 'LITERAL_SWITCH' descendant'
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
    switch (x) { // violation, 'Count of 0 for 'LITERAL_SWITCH' descendant'
      case 1:
        System.out.println("xyz");
        break;
    }
    switch (y) { // violation, 'Count of 0 for 'LITERAL_SWITCH' descendant'
      case 1:
        switch (y) { // violation, 'Count of 0 for 'LITERAL_SWITCH' descendant'
          case 2:
            System.out.println("xyz");
            break;
        }
        break;
    }
  }
}
// xdoc section -- end
