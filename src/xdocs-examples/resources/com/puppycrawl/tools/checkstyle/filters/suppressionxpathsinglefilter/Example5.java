/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantModifier"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="RedundantModifier"/>
      <property name="query" value="//INTERFACE_DEF//*"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
public interface Example5 {
  public int CONSTANT1 = 1; // filtered violation 'Redundant 'public' modifier.'
}
// xdoc section -- end
