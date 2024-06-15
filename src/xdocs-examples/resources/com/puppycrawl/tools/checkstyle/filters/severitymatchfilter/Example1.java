/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterName">
      <property name="severity" value="info"/>
    </module>
    <module name="MethodName"/>
  </module>
  <module name="SeverityMatchFilter">
    <property name="severity" value="info"/>
    <property name="acceptOnMatch" value="false"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.severitymatchfilter;

// xdoc section -- start
public class Example1 {
  public void method1(int V1){} // ok, ParameterNameCheck's severity is info

  public void Method2(){} // violation, 'must match pattern'
}
// xdoc section -- end
