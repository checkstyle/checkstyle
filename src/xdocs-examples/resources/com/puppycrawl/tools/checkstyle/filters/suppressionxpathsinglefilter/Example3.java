/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName">
      <property name="id" value="MethodName1"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="Example3\.java"/>
      <property name="id" value="MethodName1"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public class Example3 {
  // filtered violation below 'Name 'MyMethod' must match pattern'
  public void MyMethod() {}
  // filtered violation below 'Name 'MyMethod2' must match pattern'
  public void MyMethod2() {}
  // filtered violation below 'Name 'MyMethodA' must match pattern'
  public void MyMethodA() {}
  private int field = 177;
}
// xdoc section -- end
