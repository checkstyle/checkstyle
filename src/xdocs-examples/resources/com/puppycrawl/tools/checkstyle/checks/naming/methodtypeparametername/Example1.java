/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodTypeParameterName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodtypeparametername;

// xdoc section -- start
class Example1 {
  public <T> void method1() {}
  public <a> void method2() {} // violation 'Name 'a' must match pattern'
  public <K, V> void method3() {}
  public <k, V> void method4() {} // violation 'Name 'k' must match pattern'
}
// xdoc section -- end
