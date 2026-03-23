/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="basicOffset" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example5 {
  int a;                  // ok, basicOffset = 2

  void method() {
    int b = 0;            // ok, basicOffset = 2
      int c = 1;          // violation, 'level 6, expected level should be 4'
  }
}
// xdoc section -- end
