/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OperatorWrap">
      <property name="shouldWrapAtHigherLevel" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

// xdoc section - start
class Example4 {
  void example() {
    String s = "Hello" +
      "World"; // violation above ''\+' should be on a new line'

    if (10 ==
            20) { // violation above ''==' should be on a new line'
    }

    int c = 10 /
            5; // violation above ''/' should be on a new line'

    int b
            = 10;
    int e =
            10;
    b
            += 10;
    b +=
            10;
    c
            *= 10;
    c
            -= 5;
    c -=
            5;
    c
            /= 2;
    c
            %= 1;
    c
            >>= 1;
    c
        >>>= 1;
    c
            &=1 ;
    c
            <<= 1;

    int a = 1;
    int b1 = 2;
    int c1 = 3;
    int x4 = a / (b1
            - c1); // violation above ''/' should be on a new line'
  }
}
// xdoc section - end
