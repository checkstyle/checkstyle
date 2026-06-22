/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="EMPTY_STAT"/>
      <property name="limitedTokens" value="EMPTY_STAT"/>
      <property name="maximumNumber" value="0"/>
      <property name="maximumDepth" value="0"/>
      <property name="maximumMessage"
        value="Empty statement is not allowed."/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;
// xdoc section -- start
class Example12 {
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
    int b = 2;
    if (this == null || str == "abc") {
      return 0;
    }
    assert a++ == 0;
    ; // violation, 'Empty statement is not allowed'
    return 2;
  }
}
// xdoc section -- end
