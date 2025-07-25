/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions4.xml"/>
    </module>
    <module name="MethodName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example4 {

  public void TestMethod() {} // filtered violation "Name 'TestMethod' must match pattern"

}
// xdoc section -- end
