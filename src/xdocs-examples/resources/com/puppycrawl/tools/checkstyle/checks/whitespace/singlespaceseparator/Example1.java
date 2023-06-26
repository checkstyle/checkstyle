/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SingleSpaceSeparator"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlespaceseparator;

class Example1 {
  // xdoc section -- start
  int foo()   { // violation 'Use a single space'
    return  1; // violation 'Use a single space'
  }
  int fun1() {
    return 3;
  }
  void  fun2() {} // violation 'Use a single space'
  // xdoc section -- end
}
