/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionXpathFilter">
      <property name="file" value="${config.folder}/suppressions2.xml"/>
    </module>
    <module name="EmptyLineSeparator"/>
  </module>
</module>
*/

// xdoc section -- start
// filtered violation below "'package' should be separated from previous line"
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;
public class Example2 { }
// violation above, "'CLASS_DEF' should be separated from previous line"
// xdoc section -- end
