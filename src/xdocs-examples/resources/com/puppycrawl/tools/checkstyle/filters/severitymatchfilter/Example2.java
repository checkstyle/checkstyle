/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterName"/>
    <module name="MethodName">
      <property name="severity" value="info"/>
    </module>
  </module>
  <module name="SeverityMatchFilter">
    <property name="severity" value="info"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.severitymatchfilter;

// xdoc section - start
public class Example2 {
  // filtered violation below 'must match pattern'
  public void method1(int V1){} // ok, ParameterName's severity is info

  public void Method2(){} // violation, MethodName's severity is defaulted to error
}
// xdoc section - end
