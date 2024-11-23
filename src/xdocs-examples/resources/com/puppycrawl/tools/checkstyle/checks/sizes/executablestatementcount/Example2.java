/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ExecutableStatementCount">
      <property name="max" value="2"/>
      <property name="tokens" value="CTOR_DEF"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

// xdoc section -- start
class Example2 {

  Example2() { // violation, 'Executable statement count is 3 (max allowed is 2)'
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
