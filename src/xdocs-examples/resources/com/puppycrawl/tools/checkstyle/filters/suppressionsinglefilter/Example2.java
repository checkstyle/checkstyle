/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod"/>
    <module name="EqualsAvoidNull"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example2.java"/>
    <property name="checks" value="JavadocMethod"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example2.java"/>
    <property name="checks" value="EqualsAvoidNull"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example2 {

  public void checkStringEquality(String s) {
    // filtered violation below 'String literal expressions should be on the left side of an equals comparison.'
    s.equals("M");
  }
}
// xdoc section -- end
