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
class Example16 {
  void testMethod1() {
    int a = 5;
    // violation below, 'Count of 1 for 'LITERAL_ASSERT' descendant'
    assert a++ == 0 : "is not";
    System.out.println(a);
    assert a == 0 : "is not";
  }
}
// xdoc section -- end
