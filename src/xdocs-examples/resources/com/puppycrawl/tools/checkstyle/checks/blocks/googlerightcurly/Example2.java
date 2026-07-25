/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="GoogleRightCurly">
      <property name="tokens" value="LITERAL_IF, LITERAL_ELSE"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

// xdoc section -- start
public class Example2 {
  static {
    a = 0;
  } static int a = 1;

  void method() {
    if (a == 0) {
      bar(1, 2, 3);
    } // violation 'should be on the same line'
    else {
      bar(1, 2, 3);
    }

    try {
      bar(-1, -2);
    }
    catch(Exception e) {
      bar(0);
    }
    for (int i = 0; i < a; i++) {
      bar(i);}
    do {
      bar(a);
      a--;
    }
    while (a > 0);
  }

  void method2() {
    switch (a) {
      case 1: int y = 1; break;
      case 2: {bar(-1);}
      case 3: {break;}
      default: {
        bar(-2);
      }
    }
    // violation below 'should have line break'
    if (a == 0) {bar(a);} else {
      bar(a--);
    }
  }

  void bar(int... n)  {int[] k = n;}

}
// xdoc section -- end
