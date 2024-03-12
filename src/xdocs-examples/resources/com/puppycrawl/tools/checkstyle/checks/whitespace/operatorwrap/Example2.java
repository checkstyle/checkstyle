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

// xdoc section -- start
class Example2 {
  void example() {
    int b
            = 10; // violation ''=' should be on the previous line'
    int c =
            10;
    b
            += 10; // violation ''\+=' should be on the previous line'
    b +=
            10;
    c
            *= 10; // violation ''*=' should be on the previous line'
    c
            -= 5; // violation ''-=' should be on the previous line'
    c -=
            5;
    c
            /= 2; // violation ''/=' should be on the previous line'
    c
            %= 1; // violation ''%=' should be on the previous line'
    c
            >>= 1; // violation ''>>=' should be on the previous line'
    c
        >>>= 1; // violation ''>>>=' should be on the previous line'
    c
            &=1 ; // violation ''&=' should be on the previous line'
    c
            <<= 1; // violation ''<<=' should be on the previous line'
  }
}
// xdoc section -- end
