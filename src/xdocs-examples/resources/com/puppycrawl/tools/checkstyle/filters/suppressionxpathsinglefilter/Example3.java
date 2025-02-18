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

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
public class Example3 {
  // filtered violation below 'Name 'MyMethod' must match pattern'
  public void MyMethod() {}
}
// xdoc section -- end
