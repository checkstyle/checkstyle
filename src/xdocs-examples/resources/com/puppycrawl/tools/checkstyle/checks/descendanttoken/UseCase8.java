/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="LITERAL_ASSERT"/>
      <property name="limitedTokens" value="ASSIGN,DEC,INC,POST_DEC,
        POST_INC,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,
        BSR_ASSIGN,SR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,
        METHOD_CALL"/>
      <property name="maximumNumber" value="0"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;
// xdoc section -- start
class UseCase8 {
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
    assert a++ == 0; // violation, 'Count of 1 for 'LITERAL_ASSERT' descendant'
    ;
    return 2;
  }
}
// xdoc section -- end
