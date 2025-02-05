/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MagicNumber"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="Example6"/>
      <property name="checks" value="MagicNumber"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
public class Example6 {
  private int field = 177; // filtered violation ''177' is a magic number.'
}
// xdoc section -- end
