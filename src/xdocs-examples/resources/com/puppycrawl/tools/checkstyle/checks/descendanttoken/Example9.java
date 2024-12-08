/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="METHOD_DEF"/>
      <property name="limitedTokens" value="LITERAL_RETURN"/>
      <property name="maximumNumber" value="2"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example9 {
  void testMethod1() {
    int var1 = 1;
  }
  void testMethod2() {
    int var1 = 1;
    int var2 = 2;
  }

  int testMethod3(int x) { // violation 'Count of 3 for 'METHOD_DEF' descendant'
    if (x == -1) {
      return -1;
    }
    else if (x == 0) {
      return 0;
    }
    return -1;
  }
  int testMethod4(int x) { // violation 'Count of 3 for 'METHOD_DEF' descendant'
    if (x == -1) {
      return -1;
    }
    else if (x == 0) {
      return 0;
    }
    else {
      return x;
    }
  }
}
// xdoc section -- end

