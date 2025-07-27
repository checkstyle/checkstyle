/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions12.xml"/>
    </module>
    <module name="MagicNumber"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

// xdoc section -- start
public class Example12 {
  private int wordCount = 11; // filtered violation "'11' is a magic number."
}
// xdoc section -- end
