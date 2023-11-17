/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodTypeParameterName">
       <property name="format" value="^[a-zA-Z]$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodtypeparametername;

// xdoc section -- start
class Example2 {
  public <T> void method1() {}
  public <a> void method2() {}
  public <K, V> void method3() {}
  public <k, V> void method4() {}
}
// xdoc section -- end
