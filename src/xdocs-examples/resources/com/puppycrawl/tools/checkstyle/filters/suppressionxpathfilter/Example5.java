/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions5.xml"/>
    </module>
    <module name="MethodName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example5 {
  // filtered violation below "Name 'SetSomeVar' must match pattern"
  public void SetSomeVar() {}

  public void TestMethod() {} // violation, "Name 'TestMethod' must match pattern"

}
// xdoc section -- end
