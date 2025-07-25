/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions3.xml"/>
    </module>
    <module name="MethodName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example3 {

  public void TestMethodOne() { // filtered violation "Name 'TestMethodOne' must match pattern"
    int x = 5;
  }

  public void TestMethodTwo() { // violation, "Name 'TestMethodTwo' must match pattern"
    int z = 17;
  }

}
// xdoc section -- end
