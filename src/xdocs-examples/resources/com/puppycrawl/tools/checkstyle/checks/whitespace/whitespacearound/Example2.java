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
  public Example2() { }

  class Test { }
  interface testInterface { }
  class anotherTest {
    int a = 4;
  }

  int y = 0;
  int a = 4;

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
    Runnable noop = () -> { };
    try { } catch (Exception e) { }
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item : vowels) { }
    for (int i = 100; i > 10; i--) { }
    do { } while (y == 1);
    int i = 0;
    switch (i) {
      case 1: { }
    }
    int a = 4;
  }

  public static void main(String[] args) {
    if (true) { }
    else { }
    for (int i = 1; i > 1; i++) { }
    Runnable noop = () -> { };
    try { }
    catch (Exception e) { }
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) {

    }
  }

  public void muFunction() { }
  void myFunction() { }
  void myFunction2() { }
}
// xdoc section -- end
