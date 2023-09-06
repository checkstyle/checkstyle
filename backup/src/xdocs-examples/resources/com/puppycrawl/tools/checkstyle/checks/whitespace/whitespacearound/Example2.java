/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="tokens"
                value="ASSIGN, DIV_ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN, STAR_ASSIGN,
                       MOD_ASSIGN, SR_ASSIGN, BSR_ASSIGN, SL_ASSIGN, BXOR_ASSIGN,
                       BOR_ASSIGN, BAND_ASSIGN"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example2 {
  void example() {
    int b=10; // 2 violations
    // no space before and after'='
    int c = 10;
    b+=10; // 2 violations
    // no space before and after'+='
    b += 10;
    c*=10; // 2 violations
    // no space before and after'*='
    c *= 10;
    c-=5; // 2 violations
    // no space before and after'-='
    c -= 5;
    c/=2; // 2 violations
    // no space before and after'/='
    c /= 2;
    c%=1; // 2 violations
    // no space before and after'%='
    c %= 1;
    c>>=1; // 2 violations
    // no space before and after'>>='
    c >>= 1;
    c>>>=1; // 2 violations
    // no space before and after'>>>='
    c >>>= 1;
  }
}
// xdoc section -- end
