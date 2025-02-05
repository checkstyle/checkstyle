/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCheck"/>
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
  public static final int CONSTANT1 = 1;  // OK
}
// xdoc section -- end
