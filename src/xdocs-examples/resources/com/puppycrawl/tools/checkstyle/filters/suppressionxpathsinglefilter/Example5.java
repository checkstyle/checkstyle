/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="MethodName"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public class Example5 {
  // filtered violation below 'Name 'MyMethod' must match pattern'
  public void MyMethod() {}
  // filtered violation below 'Name 'MyMethod2' must match pattern'
  public void MyMethod2() {}
  // filtered violation below 'Name 'MyMethodA' must match pattern'
  public void MyMethodA() {}
  private int field = 177;
}
// xdoc section -- end
