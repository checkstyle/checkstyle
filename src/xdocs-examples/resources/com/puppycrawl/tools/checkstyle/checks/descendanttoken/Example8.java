/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="METHOD_DEF"/>
      <property name="limitedTokens" value="VARIABLE_DEF"/>
      <property name="maximumDepth" value="2"/>
      <property name="maximumNumber" value="1"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example8 {
  void testMethod1() {
    int var1 = 1;
  }
  void testMethod2() { // violation 'Count of 2 for 'METHOD_DEF' descendant'
    int var1 = 1;
    int var2 = 2;
  }

  int testMethod3(int x) {
    if (x == -1) {
      return -1;
    }
    else if (x == 0) {
      return 0;
    }
    return -1;
  }
  int testMethod4(int x) {
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

