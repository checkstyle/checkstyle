/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExecutableStatementCount"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

// xdoc section -- start
class Example1 {

  Example1() {
    int a=10;
    int b=20;
    int sub=b-a;
  }
  void testMethod1() {
    int a = 10;
    int b = 20;
    int sum = a + b;
  }
}
// xdoc section -- end
