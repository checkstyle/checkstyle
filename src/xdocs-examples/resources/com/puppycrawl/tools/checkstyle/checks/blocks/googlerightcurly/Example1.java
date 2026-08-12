/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GoogleRightCurly"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

// xdoc section - start
public class Example1 {
  static {
    a = 0;
  } static int a = 1;
  // violation above 'should be alone on a line'
  void method() {
    if (a == 0) {
      bar(1, 2, 3);
    } // violation 'should be on the same line'
    else {
      bar(1, 2, 3);
    }

    try {
      bar(-1, -2);
    } // violation 'should be on the same line'
    catch(Exception e) {
      bar(0);
    }
    for (int i = 0; i < a; i++) {
      bar(i);} // violation 'should be alone on a line'
    do {
      bar(a);
      a--;
    } // violation 'should be on the same line'
    while (a > 0);
  }
  void method2() {
    switch (a) {
      case 1: int y = 1; break;
      case 2: {bar(-1);} // violation 'should be alone on a line'
      case 3: {break;}   // violation 'should be alone on a line'
      default: {
        bar(-2);
      }
    }
    // violation below 'should have line break before'
    if (a == 0) {bar(a);} else {
      bar(a--);
    }
  }
  void bar(int... n)  {int[] k = n;}
  // violation above 'should be alone on a line'
  void foo() {
  } // violation 'Empty block should be concise {}'
  void foo1() {} static class bar1 {}
  // violation above 'should have line break after'
}
// xdoc section - end
