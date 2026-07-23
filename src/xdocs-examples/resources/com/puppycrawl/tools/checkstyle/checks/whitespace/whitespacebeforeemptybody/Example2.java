/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceBeforeEmptyBody">
          <property name="tokens"
                value="METHOD_DEF, CLASS_DEF"/>
     </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

// xdoc section -- start
class Example2 {

  void method(){}  // violation ''{' is not preceded with whitespace'

  void method2(){  // violation ''{' is not preceded with whitespace'
    // comment
  }

  void method3() {
    for (int i = 0; i < 1; i++){}

  }

  class Internal{} // violation ''{' is not preceded with whitespace'

}
// xdoc section -- end
