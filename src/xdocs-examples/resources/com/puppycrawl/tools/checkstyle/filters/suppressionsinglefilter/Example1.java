/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="AbstractComplexityCheck.java"/>
    <property name="lines" value="82,108-122"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="files" value="JavadocStyleCheck.java"/>
    <property name="lines" value="221"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="message" value="Missing a Javadoc comment"/>
  </module>
  <module name="TreeWalker">
    <module name="MagicNumber"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example1 {
  // Checkstyle: Suppress MagicNumberCheck for the MULTIPLIER variable
  private static final int MULTIPLIER = 5;

  public int calculate(int value) {
    return value * MULTIPLIER; // Normally, 5 would trigger MagicNumberCheck
  }

  // Checkstyle: Suppress JavadocStyleCheck for the methodWithoutJavadoc method
  void methodWithoutJavadoc() {
  }
}
// xdoc section -- end
