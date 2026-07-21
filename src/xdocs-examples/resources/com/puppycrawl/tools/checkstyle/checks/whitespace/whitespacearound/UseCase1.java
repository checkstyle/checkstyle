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
class UseCase1 {
  void example() {
    int b=10;
    // 2 violations above:
    // ''=' is not followed by whitespace'
    // ''=' is not preceded with whitespace'
    int c = 10;
    b += 10;
    c*=10;
    // 2 violations above:
    // ''*=' is not followed by whitespace'
    // ''*=' is not preceded with whitespace'
    c *= 10;
    c-=5;
    // 2 violations above:
    // ''-=' is not followed by whitespace'
    // ''-=' is not preceded with whitespace'
    c -= 5;
    c/=2;
    // 2 violations above:
    // ''/=' is not followed by whitespace'
    // ''/=' is not preceded with whitespace'
    c /= 2;
    c%=1;
    // 2 violations above:
    // ''%=' is not followed by whitespace'
    // ''%=' is not preceded with whitespace'
    c %= 1;
    c>>=1;
    // 2 violations above:
    // ''>>=' is not followed by whitespace'
    // ''>>=' is not preceded with whitespace'
    c >>= 1;
    c>>>=1;
    // 2 violations above:
    // ''>>>=' is not followed by whitespace'
    // ''>>>=' is not preceded with whitespace'
    c >>>= 1;
  }
}
// xdoc section -- end
