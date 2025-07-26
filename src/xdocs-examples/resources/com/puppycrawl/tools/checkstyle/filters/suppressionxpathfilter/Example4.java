/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions4.xml"/>
    </module>
    <module name="EmptyLineSeparator"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example4 {
  int y = 3;
  int x = 5; // violation, "'VARIABLE_DEF' should be separated from previous line."
  public void testMethod() {}
  // filtered violation above "'METHOD_DEF' should be separated from previous line."

}
// xdoc section -- end
