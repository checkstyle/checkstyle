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
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.severitymatchfilter;

// xdoc section - start
public class Example2 {
  // filtered violation 3 lines below 'must match pattern'
  public void method1(int V1){} // violation, ParameterName's severity is info

  public void Method2(){} // ok, MethodName's severity is defaulted to error
}
// xdoc section - end
