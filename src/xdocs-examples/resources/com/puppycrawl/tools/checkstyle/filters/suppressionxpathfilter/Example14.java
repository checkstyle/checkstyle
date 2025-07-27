/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions14.xml"/>
    </module>
    <module name="LeftCurly">
      <property name="option" value="nl"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;
import javax.annotation.processing.Generated;
// xdoc section -- start
public class Example14
{
  // violation 2 lines below "'{' at column 23 should be on a new line."
  @Generated("first")
  public void test1() {
  }

  // filtered violation 2 lines below "'{' at column 23 should be on a new line."
  @Generated("second")
  public void test2() {
  }
}
// xdoc section -- end
