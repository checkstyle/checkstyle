/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions14.xml"/>
    </module>
    <module name="MethodName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;
import javax.annotation.processing.Generated;
// xdoc section -- start
public class Example14 {
  // violation 2 lines below "Name 'Test1' must match pattern"
  @Generated("first")
  public void Test1() {}

  // filtered violation 2 lines below "Name 'Test2' must match pattern"
  @Generated("second")
  public void Test2() {}
}
// xdoc section -- end
