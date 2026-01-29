/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceBeforeEmptyBody">
      <property name="tokens" value="METHOD_DEF, CLASS_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

// xdoc section -- start
class Example2 {
  void method(){}
  // violation above, 'Whitespace is not present before the empty body of 'method''

  class Inner{}
  // violation above, 'Whitespace is not present before the empty body of 'Inner''

  interface InnerInterface{}

  enum InnerEnum{}

  static{}

  void testLoop() {
    while (true){}
  }

  Runnable lambda = () ->{};

  void validMethod() {}

  class ValidInner {}
}
// xdoc section -- end
