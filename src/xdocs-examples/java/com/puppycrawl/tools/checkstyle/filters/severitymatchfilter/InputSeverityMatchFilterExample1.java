/* Config:
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

public class InputSeverityMatchFilterExample1 {
    public void method1() {}
    public void Method2() {} // This should trigger the violation (bad naming)
}



