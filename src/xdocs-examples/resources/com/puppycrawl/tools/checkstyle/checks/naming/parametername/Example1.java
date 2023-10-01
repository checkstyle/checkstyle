/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

// xdoc section -- start
class Example1 {
  void method1(int v1) {}
  void method2(int V2) {} // violation
}
// xdoc section -- end
