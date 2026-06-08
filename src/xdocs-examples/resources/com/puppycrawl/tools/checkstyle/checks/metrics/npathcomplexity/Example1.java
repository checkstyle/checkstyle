/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NPathComplexity"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

// xdoc section -- start
public abstract class Example1 {
  int b = 0;
  public void foo() {
    int a,b,t,m,n;
    a=b=t=m=n = 0;

    print(a > b ? bar() : baz()); // 7 ternary statements multiply complexity by 2^7
    print(t > 1 ? bar() : baz());
    print(t > 2 ? bar() : baz());
    print(t > 3 ? bar() : baz());
    print(t > 4 ? bar() : baz());
    print(t > 5 ? bar() : baz());
    print(m > n ? baz() : bar());
  }

  public void boo() { // violation, NPath complexity is 217 (max allowed is 200)
    // looping through 3 switch statements produces 6^3 + 1 (217) possible outcomes
    for(int i = 0; i < b; i++) { // for statement adds 1 to final complexity
      switch(i) { // each independent switch statement multiplies complexity by 6
        case 1:    // ternary with && adds 3 to switch's complexity
          print(f(i) && g(i) ? bar() : baz());
        default:   // ternary with || adds 3 to switch's complexity
          print(f(i) || g(i) ? bar() : baz());
      }
      switch(i - 1) { // multiplies complexity by 6
        case 1:
          print(f(i) && g(i) ? bar() : baz());
        default:
          print(f(i) || g(i) ? bar() : baz());
      }
      switch(i + 1) { // multiplies complexity by 6
        case 1:
          print(f(i) && g(i) ? bar() : baz());
        default:
          print(f(i) || g(i) ? bar() : baz());
      }
    }
  }

  public abstract boolean f(int x);
  public abstract boolean g(int x);
  public abstract String bar();
  public abstract String baz();
  public abstract void print(String str);
}
// xdoc section -- end
