/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="FOR_INIT"/>
      <property name="limitedTokens" value="EXPR"/>
      <property name="minimumNumber" value="1"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example5 {
  void testMethod1() {
    for (int i = 0; i != 10; i++) {
      System.out.println(i);
    }
    int k = 0;
    for (; ; ) { // violation, 'Count of 0 for 'FOR_INIT' descendant'
      System.out.println(k);
    }
  }

  void testMethod2() {
    int[] array = new int[] {1, 2, 3, 4, 5};
    for (int i = 0; i != array.length; i++) {
      System.out.println(i);
    }
    int j = 0;
    // violation below, 'Count of 0 for 'FOR_INIT' descendant'
    for (; j != array.length;) {
      System.out.println(j);
      j++;
    }
  }
}
// xdoc section -- end
