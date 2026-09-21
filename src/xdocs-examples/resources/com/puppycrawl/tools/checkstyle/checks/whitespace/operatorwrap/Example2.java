/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OperatorWrap">
      <property name="option" value="eol"/>
      <property name="tokens"
                value="ASSIGN,DIV_ASSIGN,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,
                       MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,
                       BXOR_ASSIGN,BOR_ASSIGN,BAND_ASSIGN"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

// xdoc section - start
class Example2 {
  void example() {
    String s = "Hello" +
      "World";

    if (10 ==
            20) {
    }

    int c = 10 /
            5;

    int b
            = 10; // violation ''=' should be on the previous line'
    int e =
            10;
    b
            += 10; // violation ''\+=' should be on the previous line'
    b +=
            10;

    int a = 1;
    int b1 = 2;
    int c1 = 3;
    int x4 = a / (b1
            - c1);
  }
}
// xdoc section - end
