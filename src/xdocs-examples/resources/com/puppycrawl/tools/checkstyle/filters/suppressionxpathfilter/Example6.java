/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions6.xml"/>
    </module>
    <module name="EmptyLineSeparator">
      <property name="allowMultipleEmptyLinesInsideClassMembers"
                value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example6 {
  public void testMethod() {
    // filtered violation below 'There is more than 1 empty line after this line.'
    int testVariable;


    int num = 8;
  }

}
// xdoc section -- end
