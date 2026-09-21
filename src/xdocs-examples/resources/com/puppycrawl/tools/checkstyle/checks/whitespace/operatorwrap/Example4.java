/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OperatorWrap">
      <property name="higherLevelWrap" value="true"/>
    </module>
  </module>
</module>
<!-- padding -->
<!-- padding -->
<!-- padding -->
<!-- padding -->
<!-- padding -->
<!-- padding -->
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

// xdoc section - start
class Example4 {
  void example() {
    String s = "Hello" + // violation ''.+' should be on a new line'
      "World";

    if (10 == // violation ''==' should be on a new line'
            20) {
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
    int x4 = a / (b1 // violation ''/' should be on a new line'
            - c1);
  }
}
// xdoc section - end
