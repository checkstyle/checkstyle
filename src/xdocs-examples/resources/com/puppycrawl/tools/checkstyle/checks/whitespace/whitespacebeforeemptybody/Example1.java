/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceBeforeEmptyBody"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

// xdoc section -- start
class Example1 {

  void method(){}  // violation ''{' is not preceded with whitespace'

  void method2(){  // violation ''{' is not preceded with whitespace'
    // comment
  }

  void method3() {
    for (int i = 0; i < 1; i++){}
    // violation above ''{' is not preceded with whitespace'
  }

  class internal{} // violation ''{' is not preceded with whitespace'

}
// xdoc section -- end
