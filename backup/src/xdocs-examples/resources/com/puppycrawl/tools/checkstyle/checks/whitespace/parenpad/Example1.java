/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParenPad"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

// xdoc section -- start
class Example1 {
  int n;

  public void fun() {
    bar( 1);  // violation 'is followed by whitespace'
  }

  public void bar(int k ) {  // violation 'is preceded with whitespace'
    while (k > 0) {
    }

    StringBuilder obj = new StringBuilder(k);
  }

  public void fun2() {
    switch( n) {  // violation 'is followed by whitespace'
      case 2:
        bar(n);
      default:
        break;
    }
  }
}
// xdoc section -- end
