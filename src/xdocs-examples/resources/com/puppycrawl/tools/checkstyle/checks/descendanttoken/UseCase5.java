/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="METHOD_DEF"/>
      <property name="limitedTokens" value="EXPR"/>
      <property name="maximumNumber" value="2"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class UseCase5 {
  private int field1;
  private int field2;
  // violation below 'Count of 10 for 'METHOD_DEF' descendant'
  int testMethod(int x, String str)
          throws ArithmeticException, IllegalArgumentException {

    switch (x) {
      case 1:
        break;
      case 2:
        break;
    }

    try { }
    catch (Exception e) {
      try { }
      catch (Exception ex) { }
      return -1;
    }
    finally {
      try { }
      catch (Exception ex) { }
    }

    for (;;) {
      break;
    }
    int a = 1;
    int b = 2;
    if (this == null || str == "abc") {
      return 0;
    }
    assert a++ == 0;
    ;
    return 2;
  }
}
// xdoc section -- end
