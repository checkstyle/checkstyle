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
class UseCase7 {
  private int field1;
  private int field2;

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
    int b = 2; // violation below 'Count of 1 for 'EQUAL' descendant'
    if (this == null || str == "abc") {
      return 0;
    }
    assert a++ == 0;
    ;
    return 2;
  }
}
// xdoc section -- end
