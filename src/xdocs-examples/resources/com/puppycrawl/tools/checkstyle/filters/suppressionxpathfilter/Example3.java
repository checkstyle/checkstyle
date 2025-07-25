/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions3.xml"/>
    </module>
    <module name="LeftCurly">
      <property name="option" value="nl"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example3
{

  // filtered violation below "'{' at column 31 should be on a new line."
  public void testMethodOne() {
    int x = 5;
  }

  // violation below, "'{' at column 31 should be on a new line."
  public void testMethodTwo() {
    int z = 17;
    int y;
  }

}
// xdoc section -- end
