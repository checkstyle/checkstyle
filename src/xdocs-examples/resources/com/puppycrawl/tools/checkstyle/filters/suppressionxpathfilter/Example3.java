/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions3.xml"/>
    </module>
    <module name="LeftCurly"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example3 {

  public void testMethodOne()
  // filtered violation below "'{' at column 3 should be on the previous line."
  {
    int x = 5;
  }

  public void testMethodTwo()
  // violation below, "'{' at column 3 should be on the previous line."
  {
    int z = 17;
    int y;
  }

}
// xdoc section -- end
